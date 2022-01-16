package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.game.GameStatDto;
import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDetailDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.mapper.PlayerMapper;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.PlayPlayerRel;
import com.eliasfb.bgn.model.Player;
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

@Service
public class PlayerService {
  @Autowired private PlayerRepository repository;
  @Autowired private PlayerMapper mapper;

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
        .collect(Collectors.toList());
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
    playerDetailDto.setFavouriteGame(getFavouriteGame(playerPlays));
    playerDetailDto.setTotalWinPercentage(getTotalWinPercentage(playerPlays));
    playerDetailDto.setHighestWinRateGame(getHighestWinRateGame(playerPlays));
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
                    entry.getKey().getName(),
                    GameService.getFirstImageUrl(entry.getKey()),
                    entry.getValue().toString()))
        .orElse(null);
  }

  public GameStatDto getHighestWinRateGame(List<PlayPlayerRel> playerPlays) {
    Map<Game, Double> winRatesGroupedByGame =
        playerPlays.stream()
            .collect(
                Collectors.toMap(
                    play -> play.getId().getPlay().getGame(),
                    play -> getGameWinPercentage(play, playerPlays),
                    (a, b) -> DoubleStream.of(a, b).average().orElse(0),
                    TreeMap::new));
    double maxWinrateOfPlays =
        winRatesGroupedByGame.values().stream().mapToDouble(n -> n).max().orElse(0);
    Comparator<Map.Entry<Game, Double>> mostPlaysComparator =
        (left, right) ->
            getNumberOfPlaysOnGame(right.getKey(), playerPlays)
                .compareTo(getNumberOfPlaysOnGame(left.getKey(), playerPlays));
    // We obtain the game with most plays amongst the games with the highest winrate
    return winRatesGroupedByGame.entrySet().stream()
        .filter(e -> maxWinrateOfPlays == e.getValue())
        .sorted(mostPlaysComparator)
        .findFirst()
        .map(
            entry ->
                new GameStatDto(
                    entry.getKey().getName(),
                    GameService.getFirstImageUrl(entry.getKey()),
                    entry.getValue().toString(),
                    getNumberOfPlaysOnGame(entry.getKey(), playerPlays).toString()))
        .orElse(null);
  }

  public Long getNumberOfPlaysOnGame(Game game, List<PlayPlayerRel> playerPlays) {
    return playerPlays.stream()
        .filter(playerPlay -> playerPlay.getId().getPlay().getGame().equals(game))
        .count();
  }

  public Double getGameWinPercentage(PlayPlayerRel play, List<PlayPlayerRel> playerPlays) {
    List<PlayPlayerRel> gamePlays =
        playerPlays.stream()
            .filter(
                playLoop ->
                    playLoop
                        .getId()
                        .getPlay()
                        .getGame()
                        .getId()
                        .equals(play.getId().getPlay().getGame().getId()))
            .collect(Collectors.toList());
    return getTotalWinPercentage(gamePlays);
  }

  public Double getTotalWinPercentage(List<PlayPlayerRel> playerPlays) {
    double numPlaysWoned = playerPlays.stream().filter(play -> play.isWinner()).count();
    double totalPlays = playerPlays.size();
    return roundDoubleWithNDecimals(((numPlaysWoned / totalPlays) * 100), 2);
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
