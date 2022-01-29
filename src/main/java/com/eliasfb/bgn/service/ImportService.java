package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.bgstats.BgStatsDto;
import com.eliasfb.bgn.dto.bgstats.BgStatsGameDto;
import com.eliasfb.bgn.dto.bgstats.BgStatsPlayDto;
import com.eliasfb.bgn.model.*;
import com.eliasfb.bgn.repository.GameRepository;
import com.eliasfb.bgn.repository.PlayRepository;
import com.eliasfb.bgn.repository.PlayerRepository;
import com.eliasfb.bgn.repository.VictoryRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.eliasfb.bgn.service.DateService.STANDARD_DATE_FORMAT;

@Service
public class ImportService {

  @Autowired PlayerRepository playerRespository;

  @Autowired PlayRepository playRespository;

  @Autowired GameRepository gameRepository;

  @Autowired VictoryRepository victoryRepository;

  @Transactional
  public ResponseDto importGames() throws IOException {
    List<Victory> victories = victoryRepository.findAll();
    Victory competitiveVictory =
        victories.stream()
            .filter(victory -> "Competitivo".equals(victory.getName()))
            .findFirst()
            .orElse(null);
    Victory cooperativeVictory =
        victories.stream()
            .filter(victory -> "Cooperativo".equals(victory.getName()))
            .findFirst()
            .orElse(null);
    AtomicReference<Integer> numGamesInserted = new AtomicReference<>(0);
    Set<String> registeredGameOfficialNames =
        this.gameRepository.findAll().stream()
            .map(game -> game.getOfficialName())
            .collect(Collectors.toSet());
    BgStatsDto dto = getBgStatsDtoFromFile();
    List<BgStatsGameDto> newGames =
        dto.getGames().stream()
            .filter(game -> !registeredGameOfficialNames.contains(game.getName()))
            .collect(Collectors.toList());
    newGames.stream()
        .map(
            bgGame ->
                Game.builder()
                    .name(bgGame.getName())
                    .officialName(bgGame.getName())
                    .minPlayers(bgGame.getMinPlayerCount())
                    .maxPlayers(bgGame.getMaxPlayerCount())
                    .victory(bgGame.isCooperative() ? cooperativeVictory : competitiveVictory)
                    .owned(false)
                    .scorable(!bgGame.isNoPoints())
                    .build())
        .forEach(
            game -> {
              this.gameRepository.save(game);
              numGamesInserted.getAndSet(numGamesInserted.get() + 1);
            });
    return new ResponseDto(
        ResponseDto.OK_CODE, numGamesInserted.get() + " games imported successfully");
  }

  @Transactional
  public ResponseDto importPlays() throws IOException {
    AtomicReference<Integer> numPlaysInserted = new AtomicReference<>(0);
    BgStatsDto dto = getBgStatsDtoFromFile();
    List<Player> registeredPlayers = this.playerRespository.findAll();
    List<Play> registeredPlays = this.playRespository.findAll();
    List<Game> registeredGames = this.gameRepository.findAll();
    // We need to first obtain the plays to be inserted : Plays that don't already exist on the DB
    List<BgStatsPlayDto> playsToBeInserted =
        getNewPlaysToBeInserted(dto.getPlays(), registeredPlays);
    playsToBeInserted.stream()
        .forEach(
            playToBeInserted -> {
              try {
                this.playRespository.save(
                    createPlayFromBgStatsPlay(
                        playToBeInserted, registeredGames, registeredPlayers));
              } catch (ParseException e) {
                e.printStackTrace();
              }
              numPlaysInserted.getAndSet(numPlaysInserted.get() + 1);
            });
    return new ResponseDto(
        ResponseDto.OK_CODE, numPlaysInserted.get() + " plays imported successfully");
  }

  private Play createPlayFromBgStatsPlay(
      BgStatsPlayDto playToBeInserted, List<Game> registeredGames, List<Player> registeredPlayers)
      throws ParseException {
    Play play =
        new Play(
            new Date(STANDARD_DATE_FORMAT.parse(playToBeInserted.getPlayDate()).getTime()),
            registeredGames.stream()
                .filter(
                    registeredGame ->
                        registeredGame.getOfficialName().equals(playToBeInserted.getGameName()))
                .findFirst()
                .orElse(null));
    play.setPlayers(
        playToBeInserted.getPlayerScores().stream()
            .map(
                playerScore ->
                    new PlayPlayerRel(
                        new PlayPlayerRelId(
                            play,
                            registeredPlayers.stream()
                                .filter(
                                    player -> playerScore.getPlayerName().equals(player.getName()))
                                .findFirst()
                                .orElse(null)),
                        getScoreFromBgStatsScore(playerScore.getScore()),
                        playerScore.isWinner()))
            .collect(Collectors.toList()));
    return play;
  }

  private int getScoreFromBgStatsScore(String bgStatsScore) {
    int score = 0;
    if (!StringUtils.isEmpty(bgStatsScore)) {
      try {
        score = Integer.parseInt(bgStatsScore);
      } catch (NumberFormatException e) {

      }
    }
    return score;
  }

  private List<BgStatsPlayDto> getPlaysFromRegisteredGames(
      BgStatsDto dto, List<Game> registeredGames) {
    try {
      return dto.getPlays().stream()
          .filter(
              play ->
                  registeredGames.stream()
                      .anyMatch(game -> game.getOfficialName().equals(play.getGameName())))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  private List<BgStatsPlayDto> getNewPlaysToBeInserted(
      List<BgStatsPlayDto> playsFromRegisteredGames, List<Play> registeredPlays) {
    try {
      return playsFromRegisteredGames.stream()
          .filter(
              play ->
                  registeredPlays.isEmpty()
                      || registeredPlays.stream()
                          .noneMatch(
                              registeredPlay ->
                                  registeredPlay
                                      .getId()
                                      .equals(playsFromRegisteredGames.indexOf(play) + 1)))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }

  @Transactional
  public ResponseDto importPlayers() throws IOException {
    AtomicReference<Integer> numPlayersInserted = new AtomicReference<>(0);
    BgStatsDto dto = getBgStatsDtoFromFile();
    // Non existent player names are inserted as new players
    dto.getPlayers().stream()
        .filter(player -> this.playerRespository.findByName(player.getName()) == null)
        .forEach(
            newPlayer -> {
              this.playerRespository.save(new Player(newPlayer.getName()));
              numPlayersInserted.getAndSet(numPlayersInserted.get() + 1);
            });
    return new ResponseDto(
        ResponseDto.OK_CODE, numPlayersInserted.get() + " players imported successfully");
  }

  private BgStatsDto getBgStatsDtoFromFile() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    BgStatsDto dto =
        objectMapper.readValue(new File("src/main/resources/BGStatsExport.json"), BgStatsDto.class);
    return fillBgStatsDtoWithPlayerNames(fillBgStatsDtoWithGameNames(dto));
  }

  private BgStatsDto fillBgStatsDtoWithGameNames(BgStatsDto dto) {
    dto.getPlays()
        .forEach(
            play ->
                play.setGameName(
                    dto.getGames().stream()
                        .filter(game -> game.getId().equals(play.getGameRefId()))
                        .findFirst()
                        .map(game -> game.getName())
                        .orElse(null)));
    return dto;
  }

  private BgStatsDto fillBgStatsDtoWithPlayerNames(BgStatsDto dto) {
    dto.getPlays()
        .forEach(
            play ->
                play.getPlayerScores()
                    .forEach(
                        playerScore ->
                            playerScore.setPlayerName(
                                dto.getPlayers().stream()
                                    .filter(
                                        player ->
                                            player.getId().equals(playerScore.getPlayerRefId()))
                                    .findFirst()
                                    .map(player -> player.getName())
                                    .orElse(null))));
    return dto;
  }
}
