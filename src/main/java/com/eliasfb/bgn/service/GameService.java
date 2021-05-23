package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.game.CreateGameDto;
import com.eliasfb.bgn.dto.game.GameDetailDto;
import com.eliasfb.bgn.dto.game.GameDto;
import com.eliasfb.bgn.dto.game.GameLocationUpdateDto;
import com.eliasfb.bgn.mapper.GameMapper;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.Location;
import com.eliasfb.bgn.model.Score;
import com.eliasfb.bgn.repository.GameRepository;
import com.eliasfb.bgn.repository.LocationRepository;
import com.eliasfb.bgn.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.eliasfb.bgn.service.DateService.DATE_FORMAT;

@Service
public class GameService {
  @Autowired private GameRepository repository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private ScoreRepository scoreRepository;
  @Autowired private GameMapper mapper;

  private static final List<String> DEFAULT_SCORES =
      Arrays.asList("Temática", "Mecánicas", "Arte", "Originalidad");

  public List<GameDto> findAll() {
    return this.mapper.gameToGameDtoList(this.repository.findAll());
  }

  public GameDetailDto findById(Integer id) {
    return this.mapper.gameToGameDetailDto(this.repository.findById(id));
  }

  @Transactional
  public ResponseDto create(CreateGameDto dto) {
    ResponseDto responseDto = new ResponseDto(ResponseDto.OK_CODE, "Game created successfully");
    Game gameCreated = this.repository.save(this.mapper.createGameDtoToGame(dto));
    // Save default scores
    DEFAULT_SCORES.stream()
        .map(s -> new Score(gameCreated.getId(), s, 0))
        .forEach(s -> this.scoreRepository.save(s));
    return responseDto;
  }

  @Transactional
  public ResponseDto updateLocation(Integer gameId, GameLocationUpdateDto dto) {
    ResponseDto responseDto =
        new ResponseDto(ResponseDto.OK_CODE, "Game location updated successfully");
    Game game = this.repository.findById(gameId);
    Location location = this.locationRepository.findById(dto.getLocationId());
    game.setLocation(location);
    this.repository.save(game);
    return responseDto;
  }

  @Transactional
  public ResponseDto registerPlay(Integer gameId) {
    ResponseDto responseDto =
        new ResponseDto(ResponseDto.OK_CODE, "Game play registered successfully");
    Game game = this.repository.findById(gameId);
    game.setLastPlayedDate(LocalDateTime.now().format(DATE_FORMAT));
    this.repository.save(game);
    return responseDto;
  }

  @Transactional
  public Double updateScoreValue(Integer gameId, Integer scoreId, Integer value) {
    Score score = this.scoreRepository.findById(scoreId);
    score.setValue(value);
    this.scoreRepository.save(score);
    return this.repository.findById(gameId).getAverageScore();
  }
}
