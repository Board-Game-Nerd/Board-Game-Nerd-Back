package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.play.CreatePlayDto;
import com.eliasfb.bgn.dto.play.PlayDetailDto;
import com.eliasfb.bgn.dto.play.PlayDto;
import com.eliasfb.bgn.mapper.PlayMapper;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.Play;
import com.eliasfb.bgn.model.PlayPlayerRel;
import com.eliasfb.bgn.repository.GameRepository;
import com.eliasfb.bgn.repository.PlayRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayService {
  @Autowired private GameRepository gameRepository;
  @Autowired private PlayRepository repository;
  @Autowired private PlayMapper mapper;
  @Autowired private PlayerService playerService;

  public List<PlayDto> findAll() {
    return this.mapper.playsToPlaysDto(
        this.repository.findAll(Sort.by(Sort.Direction.DESC, "date")));
  }

  @Transactional
  public ResponseDto create(CreatePlayDto playDto) {
    ResponseDto responseDto = new ResponseDto(ResponseDto.OK_CODE, "Play created successfully");
    Play playToCreate = this.mapper.createPlayToPlay(playDto);
    playToCreate.setGame(this.gameRepository.findById(playDto.getGameId()));
    playToCreate.setDate(new Date(System.currentTimeMillis()));
    this.repository.save(playToCreate);
    return responseDto;
  }

  public PlayDetailDto findById(int id) {
    Play play = this.repository.findById(id);
    Game gamePlayed = play.getGame();
    Map<Integer, PlayerGameStats> playerWinRatesByPlayerId =
        play.getPlayers().stream()
            .collect(
                Collectors.toMap(
                    playPlayerRel -> playPlayerRel.getId().getPlayer().getId(),
                    playPlayerRel -> {
                      List<PlayPlayerRel> playerPlaysOfGame =
                          this.playerService.getPlaysOfGame(
                              playPlayerRel.getId().getPlayer().getPlays(), gamePlayed);
                      return new PlayerGameStats(
                          this.playerService.getWinPercentage(playerPlaysOfGame),
                          gamePlayed.isScorable()
                              ? this.playerService.getMaxScore(playerPlaysOfGame)
                              : null,
                          gamePlayed.isScorable()
                              ? this.playerService.getAvgScore(playerPlaysOfGame)
                              : null);
                    }));
    PlayDetailDto playDetailDto = this.mapper.playToPlayDetailDto(play);
    playDetailDto
        .getPlayers()
        .forEach(
            playDetailPlayerDto -> {
              PlayerGameStats stats =
                  playerWinRatesByPlayerId.get(playDetailPlayerDto.getPlayer().getId());
              playDetailPlayerDto.setWinPercentage(stats.winPercentage);
              playDetailPlayerDto.setMaxScore(stats.maxScore);
              playDetailPlayerDto.setAvgScore(stats.avgScore);
            });
    return playDetailDto;
  }

  @Data
  @AllArgsConstructor
  private class PlayerGameStats {
    private Double winPercentage;
    private Integer maxScore;
    private Integer avgScore;
  }

  public List<Integer> findIds() {
    return this.repository.findAll().stream().map(p -> p.getId()).collect(Collectors.toList());
  }
}
