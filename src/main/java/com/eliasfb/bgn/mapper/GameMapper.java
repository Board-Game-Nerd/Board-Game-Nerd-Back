package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.GameExpansion;
import com.eliasfb.bgn.model.GamePlayPlayerSelection;
import com.eliasfb.bgn.model.GamePlayerSelectionRel;
import com.eliasfb.bgn.openapi.model.*;
import com.mysql.cj.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

  String BACKEND_HOST = "http://192.168.0.25:8080";

  default List<GameDto> gameToGameDtoList(List<Game> games) {
    return games.stream().map(g -> this.gameToGameDto(g)).collect(Collectors.toList());
  }

  @Mapping(source = "victory.name", target = "victory")
  @Mapping(source = "theme.name", target = "theme")
  @Mapping(source = "complexity.name", target = "complexity")
  @Mapping(source = "location.name", target = "location")
  @Mapping(source = "averageScore", target = "avgScore")
  @Mapping(source = "medium.name", target = "medium")
  @Mapping(target = "featuresDisabled", ignore = true)
  GameDto basicGameToGameDto(Game game);

  default GameDto gameToGameDto(Game game) {
    GameDto gameDto = this.basicGameToGameDto(game);
    gameDto.setImageUrl(
        game.getImages().stream()
            .map(g -> BACKEND_HOST + "/images/" + g.getName())
            .findFirst()
            .orElse(null));
    if (!StringUtils.isEmptyOrWhitespaceOnly(game.getFeaturesDisabled())) {
      gameDto.setFeaturesDisabled(
          Arrays.asList(game.getFeaturesDisabled().split(",")).stream()
              .map(featureDisabled -> FeatureDto.fromValue(featureDisabled))
              .collect(Collectors.toList()));
    }
    gameDto.setNumPlays(game.getPlays().size());
    gameDto.setPlayedBy(
        game.getPlays().stream()
            .flatMap(play -> play.getPlayers().stream())
            .map(playPlayerRel -> playPlayerRel.getId().getPlayer().getName())
            .distinct()
            .collect(Collectors.toList()));
    return gameDto;
  }

  @Mapping(target = "name", source = "id.name")
  GameExpansionDto gameExpansionToGameExpansionDto(GameExpansion gameExpansion);

  @Mapping(source = "location.name", target = "location")
  @Mapping(source = "victory.name", target = "victory")
  @Mapping(source = "theme.name", target = "theme")
  @Mapping(source = "complexity.name", target = "complexity")
  @Mapping(source = "medium.name", target = "medium")
  @Mapping(source = "style.name", target = "style")
  @Mapping(source = "scores", target = "scoreInfo.scores")
  @Mapping(source = "averageScore", target = "scoreInfo.avgValue")
  @Mapping(target = "expansions.content", source = "expansions")
  GameDetailDto basicGameToGameDetailDto(Game game);

  default GameDetailDto gameToGameDetailDto(Game game) {
    GameDetailDto gameDetailDto = this.basicGameToGameDetailDto(game);
    gameDetailDto.setImages(
        game.getImages().stream()
            .map(g -> new ImageDto().src(BACKEND_HOST + "/images/" + g.getName()))
            .collect(Collectors.toList()));
    gameDetailDto.setRulesUrl(BACKEND_HOST + "/rules/" + game.getName().replace(" ", "") + ".pdf");
    long expansionsOwned =
        gameDetailDto.getExpansions().getContent().stream().filter(ex -> ex.getOwned()).count();
    float expansionsPercentage =
        (float) expansionsOwned / (float) gameDetailDto.getExpansions().getContent().size();
    gameDetailDto.getExpansions().setPercentageOwned(Math.round(expansionsPercentage * 100));
    gameDetailDto.setNumPlays(game.getPlays().size());
    gameDetailDto.setPlayConfiguration(getGamePlayConfigurationDtoFromGame(game));
    return gameDetailDto;
  }

  default GamePlayConfigurationDto getGamePlayConfigurationDtoFromGame(Game game) {
    GamePlayConfigurationDto gamePlayConfigurationDto =
        new GamePlayConfigurationDto().isScorable(game.isScorable());
    List<GamePlayerSelectionRel> gamePlayerSelectionRels = game.getGamePlayerSelections();
    if (!CollectionUtils.isEmpty(gamePlayerSelectionRels)) {
      List<GamePlayPlayerSelectionOptionDto> firstLevelSelection =
          game.getGamePlayerSelections().stream()
              .sorted(Comparator.comparingInt(GamePlayerSelectionRel::getSequence))
              .map(
                  gamePlayerSelectionRel -> {
                    GamePlayPlayerSelection playerSelection =
                        gamePlayerSelectionRel.getId().getPlayerSelection();
                    GamePlayPlayerSelectionOptionDto option =
                        createGamePlayPlayerSelectionOptionDto(playerSelection.getName());
                    if (!StringUtils.isNullOrEmpty(playerSelection.getSubselection())) {
                      option =
                          option.subSelection(
                              new GamePlayPlayerSelectionDto()
                                  .items(
                                      Arrays.asList(playerSelection.getSubselection().split(","))
                                          .stream()
                                          .map(
                                              subselectionName ->
                                                  createGamePlayPlayerSelectionOptionDto(
                                                      subselectionName))
                                          .collect(Collectors.toList())));
                    }
                    return option;
                  })
              .collect(Collectors.toList());
      gamePlayConfigurationDto =
          gamePlayConfigurationDto.playerSelection(
              new GamePlayPlayerSelectionDto().items(firstLevelSelection));
    }
    return gamePlayConfigurationDto;
  }

  default GamePlayPlayerSelectionOptionDto createGamePlayPlayerSelectionOptionDto(
      String playerSelectionName) {
    return new GamePlayPlayerSelectionOptionDto()
        .name(playerSelectionName)
        .imageUrl(
            BACKEND_HOST
                + "/images/playerselection/"
                + playerSelectionName.replace(" ", "").toLowerCase()
                + ".jpg");
  }

  @Mapping(source = "themeId", target = "theme.id")
  @Mapping(source = "complexityId", target = "complexity.id")
  @Mapping(source = "locationId", target = "location.id")
  @Mapping(source = "mediumId", target = "medium.id")
  @Mapping(source = "styleId", target = "style.id")
  @Mapping(source = "victoryId", target = "victory.id")
  Game basicCreateGameDtoToGame(CreateGameDto dto);

  default Game createGameDtoToGame(CreateGameDto dto) {
    Game game = basicCreateGameDtoToGame(dto);
    game.setOfficialName(dto.getName());
    game.setOwned(true);
    return game;
  }
}
