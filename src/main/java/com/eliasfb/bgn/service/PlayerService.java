package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.game.GameStatDto;
import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDetailDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.dto.player.group.PlayerGroupDto;
import com.eliasfb.bgn.mapper.PlayerMapper;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.PlayPlayerRel;
import com.eliasfb.bgn.model.Player;
import com.eliasfb.bgn.model.PlayerGroup;
import com.eliasfb.bgn.repository.PlayerGroupRepository;
import com.eliasfb.bgn.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static com.eliasfb.bgn.service.DateService.STANDARD_DATE_FORMAT;

@Service
public class PlayerService {
  @Autowired private PlayerRepository repository;
  @Autowired private PlayerGroupRepository playerGroupRepository;
  @Autowired private PlayerMapper mapper;

  public List<Integer> findIds() {
    return this.repository.findAll().stream().map(p -> p.getId()).collect(Collectors.toList());
  }

  public List<PlayerDto> findAll() {
    List<Player> players = this.repository.findAll();
    // We fill each player with its number of plays
    return players.stream()
        .map(
            player -> {
              PlayerDto playerDto = this.mapper.playerToPlayerDto(player);
              playerDto.setNumPlays(player.getPlays().size());
              return playerDto;
            })
        .sorted(
            (leftPlayer, rightPlayer) ->
                rightPlayer.getNumPlays().compareTo(leftPlayer.getNumPlays()))
        .collect(Collectors.toList());
  }

  public List<PlayerGroupDto> findAllPlayerGroups() {
    List<PlayerGroup> playerGroups = this.playerGroupRepository.findAll();
    List<PlayerGroupDto> playerGroupDtos =
        playerGroups.stream()
            .map(playerGroup -> this.mapper.playerGroupToPlayerGroupDto(playerGroup))
            .collect(Collectors.toList());
    playerGroups.forEach(
        playerGroup ->
            playerGroupDtos.stream()
                .filter(playerGroupDto -> playerGroupDto.getId().equals(playerGroup.getId()))
                .findFirst()
                .ifPresent(
                    playerGroupDto ->
                        playerGroupDto.setPlayers(
                            playerGroup.getPlayers().stream()
                                .map(
                                    playerRel ->
                                        this.mapper.playerToPlayerDto(
                                            playerRel.getId().getPlayer()))
                                .collect(Collectors.toList()))));
    return playerGroupDtos;
  }

  public PlayerDetailDto findById(Integer playerId) {
    Player player = this.repository.findById(playerId);
    PlayerDetailDto playerDetailDto = this.mapper.playerToPlayerDetailDto(player);
    return fillPlayerDetailWithPlayStats(playerDetailDto, player.getPlays());
  }

  public PlayerDetailDto fillPlayerDetailWithPlayStats(
      PlayerDetailDto playerDetailDto, List<PlayPlayerRel> playerPlays) {
    playerDetailDto.setNumPlays(playerPlays.size());
    playerDetailDto.setHighestPlayScore(
        playerPlays.stream().mapToInt(play -> play.getScore()).max().orElseGet(null));
    playerDetailDto.setTotalWinPercentage(getWinPercentage(playerPlays));
    playerDetailDto.setFavouriteGame(getFavouriteGame(playerPlays));
    playerDetailDto.setHighestWinRateGame(getHighestWinRateGame(playerPlays));
    playerDetailDto.setLatestPlayedGame(getLatestPlayedGame(playerPlays));
    return playerDetailDto;
  }

  public GameStatDto getFavouriteGame(List<PlayPlayerRel> playerPlays) {
    Map<Game, Integer> gamePlaysGroupedByGame =
        playerPlays.stream()
            .collect(
                Collectors.toMap(
                    play -> play.getId().getPlay().getGame(), play -> 1, Integer::sum));
    int maxNumberOfPlays =
        gamePlaysGroupedByGame.values().stream().mapToInt(n -> n).max().orElse(0);
    return gamePlaysGroupedByGame.entrySet().stream()
        .filter(e -> maxNumberOfPlays == e.getValue())
        .findFirst()
        .map(
            entry ->
                new GameStatDto(
                    entry.getKey().getId(),
                    entry.getKey().getName(),
                    GameService.getFirstImageUrl(entry.getKey()),
                    entry.getValue().toString()))
        .orElse(null);
  }

  public GameStatDto getHighestWinRateGame(List<PlayPlayerRel> allPlayerPlays) {
    List<PlayPlayerRel> playerPlaysOfNonCooperativeGames =
        getPlaysOfNonCooperativeGame(allPlayerPlays);
    Map<Game, Double> winRatesGroupedByGame =
        playerPlaysOfNonCooperativeGames.stream()
            .collect(
                Collectors.toMap(
                    play -> play.getId().getPlay().getGame(),
                    play ->
                        getWinPercentage(
                            getPlaysOfGame(
                                playerPlaysOfNonCooperativeGames,
                                play.getId().getPlay().getGame())),
                    (a, b) -> DoubleStream.of(a, b).average().orElse(0),
                    TreeMap::new));
    double maxWinrateOfPlays =
        winRatesGroupedByGame.values().stream().mapToDouble(n -> n).max().orElse(0);
    Comparator<Map.Entry<Game, Double>> mostPlaysComparator =
        (left, right) ->
            getNumberOfPlaysOnGame(right.getKey(), playerPlaysOfNonCooperativeGames)
                .compareTo(getNumberOfPlaysOnGame(left.getKey(), playerPlaysOfNonCooperativeGames));
    // We obtain the game with most plays amongst the games with the highest winrate
    return winRatesGroupedByGame.entrySet().stream()
        .filter(e -> maxWinrateOfPlays == e.getValue())
        .sorted(mostPlaysComparator)
        .findFirst()
        .map(
            entry ->
                new GameStatDto(
                    entry.getKey().getId(),
                    entry.getKey().getName(),
                    GameService.getFirstImageUrl(entry.getKey()),
                    entry.getValue().toString(),
                    getNumberOfPlaysOnGame(entry.getKey(), playerPlaysOfNonCooperativeGames)
                        .toString()))
        .orElse(null);
  }

  public GameStatDto getLatestPlayedGame(List<PlayPlayerRel> playerPlays) {
    return playerPlays.stream()
        .sorted(
            (leftPlay, rightPlay) ->
                rightPlay
                    .getId()
                    .getPlay()
                    .getDate()
                    .compareTo(leftPlay.getId().getPlay().getDate()))
        .findFirst()
        .map(
            latestPlay ->
                new GameStatDto(
                    latestPlay.getId().getPlay().getGame().getId(),
                    latestPlay.getId().getPlay().getGame().getName(),
                    GameService.getFirstImageUrl(latestPlay.getId().getPlay().getGame()),
                    STANDARD_DATE_FORMAT.format(latestPlay.getId().getPlay().getDate())))
        .orElse(null);
  }

  public Long getNumberOfPlaysOnGame(Game game, List<PlayPlayerRel> playerPlays) {
    return playerPlays.stream()
        .filter(playerPlay -> playerPlay.getId().getPlay().getGame().equals(game))
        .count();
  }

  public Double getWinPercentage(List<PlayPlayerRel> playerPlaysToBeConsidered) {
    double numPlaysWoned =
        playerPlaysToBeConsidered.stream().filter(play -> play.isWinner()).count();
    double totalPlays = playerPlaysToBeConsidered.size();
    return roundDoubleWithNDecimals(((numPlaysWoned / totalPlays) * 100), 2);
  }

  public Integer getMaxScore(List<PlayPlayerRel> playerPlaysToBeConsidered) {
    return playerPlaysToBeConsidered.stream()
        .mapToInt(playPlayerRel -> playPlayerRel.getScore())
        .max()
        .orElse(0);
  }

  public Integer getAvgScore(List<PlayPlayerRel> playerPlaysToBeConsidered) {
    return (int)
        playerPlaysToBeConsidered.stream()
            .mapToInt(playPlayerRel -> playPlayerRel.getScore())
            .average()
            .orElse(0);
  }

  public List<PlayPlayerRel> getPlaysOfGame(List<PlayPlayerRel> allPlayerPlays, Game game) {
    return allPlayerPlays.stream()
        .filter(play -> game == null || game.equals(play.getId().getPlay().getGame()))
        .collect(Collectors.toList());
  }

  public List<PlayPlayerRel> getPlaysOfNonCooperativeGame(List<PlayPlayerRel> allPlayerPlays) {
    return allPlayerPlays.stream()
        .filter(
            play -> !"Cooperativo".equals(play.getId().getPlay().getGame().getVictory().getName()))
        .collect(Collectors.toList());
  }

  public static double roundDoubleWithNDecimals(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  @Transactional
  public ResponseDto create(CreatePlayerDto playerDto) {
    ResponseDto responseDto = new ResponseDto(ResponseDto.OK_CODE, "Player created successfully");
    if (!StringUtils.isEmpty(playerDto.getName())) {
      this.repository.save(this.mapper.createPlayerToPlayer(playerDto));
    }
    return responseDto;
  }
}
