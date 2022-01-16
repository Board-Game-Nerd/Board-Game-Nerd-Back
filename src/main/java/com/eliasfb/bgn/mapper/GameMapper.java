package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.game.CreateGameDto;
import com.eliasfb.bgn.dto.game.GameDetailDto;
import com.eliasfb.bgn.dto.game.GameDto;
import com.eliasfb.bgn.dto.game.GameExpansionDto;
import com.eliasfb.bgn.model.Game;
import com.eliasfb.bgn.model.GameExpansion;
import com.mysql.cj.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

  String BACKEND_HOST = "http://192.168.0.25:8080";

  default List<GameDto> gameToGameDtoList(List<Game> games) {
    return games.stream().map(g -> this.gameToGameDto(g)).collect(Collectors.toList());
  }

  @Mapping(source = "location.name", target = "location")
  @Mapping(source = "victory.name", target = "victory")
  @Mapping(source = "theme.name", target = "theme")
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
      gameDto.setFeaturesDisabled(Arrays.asList(game.getFeaturesDisabled().split(",")));
    }
    return gameDto;
  }

  @Mapping(target = "name", source = "id.name")
  GameExpansionDto gameExpansionToGameExpansionDto(GameExpansion gameExpansion);

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
    gameDetailDto.setImageUrls(
        game.getImages().stream()
            .map(g -> BACKEND_HOST + "/images/" + g.getName())
            .collect(Collectors.toList()));
    gameDetailDto.setRulesUrl(BACKEND_HOST + "/rules/" + game.getName().replace(" ", "") + ".pdf");
    long expansionsOwned =
        gameDetailDto.getExpansions().getContent().stream().filter(ex -> ex.getOwned()).count();
    float expansionsPercentage =
        (float) expansionsOwned / (float) gameDetailDto.getExpansions().getContent().size();
    gameDetailDto.getExpansions().setPercentageOwned(Math.round(expansionsPercentage * 100));
    gameDetailDto.setNumPlays(game.getPlays().size());
    return gameDetailDto;
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
    return game;
  }
}
