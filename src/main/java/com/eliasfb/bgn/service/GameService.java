package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.game.CreateGameDto;
import com.eliasfb.bgn.dto.game.GameDetailDto;
import com.eliasfb.bgn.dto.game.GameDto;
import com.eliasfb.bgn.dto.game.GameLocationUpdateDto;
import com.eliasfb.bgn.mapper.GameMapper;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.Image;
import com.eliasfb.bgn.model.Location;
import com.eliasfb.bgn.model.Score;
import com.eliasfb.bgn.repository.GameRepository;
import com.eliasfb.bgn.repository.ImageRepository;
import com.eliasfb.bgn.repository.LocationRepository;
import com.eliasfb.bgn.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.eliasfb.bgn.mapper.GameMapper.BACKEND_HOST;

@Service
public class GameService {
  @Autowired private GameRepository repository;
  @Autowired private LocationRepository locationRepository;
  @Autowired private ScoreRepository scoreRepository;
  @Autowired private ImageRepository imageRepository;
  @Autowired private GameMapper mapper;

  private static final List<String> DEFAULT_SCORES =
      Arrays.asList("Temática", "Mecánicas", "Arte", "Originalidad");

  public List<GameDto> findAll() {
    List<Game> games = this.repository.findAllByOwned(true);
    List<GameDto> gameDtos = this.mapper.gameToGameDtoList(games);
    return gameDtos.stream()
        .sorted((g1, g2) -> g2.getNumPlays().compareTo(g1.getNumPlays()))
        .collect(Collectors.toList());
  }

  public List<Integer> findIds() {
    return this.repository.findAllByOwned(true).stream()
        .map(g -> g.getId())
        .collect(Collectors.toList());
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
    imageRepository.save(
        new Image(
            gameCreated.getId(),
            gameCreated.getName().replace(" ", "").toLowerCase().concat(".jpg"),
            0));
    return responseDto;
  }

  @Transactional
  public GameDetailDto updateFavoriteStatus(int id) {
    this.repository.setFavorite(id);
    return this.findById(id);
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
  public Double updateScoreValue(Integer gameId, Integer scoreId, Integer value) {
    Score score = this.scoreRepository.findById(scoreId);
    score.setValue(value);
    this.scoreRepository.save(score);
    return this.repository.findById(gameId).getAverageScore();
  }

  public static String getFirstImageUrl(Game game) {
    return game.getImages().stream()
        .map(g -> BACKEND_HOST + "/images/" + g.getName())
        .findFirst()
        .orElse(null);
  }
}
