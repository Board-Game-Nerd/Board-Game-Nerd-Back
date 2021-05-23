package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.game.CreateGameDto;
import com.eliasfb.bgn.dto.game.GameDetailDto;
import com.eliasfb.bgn.dto.game.GameDto;
import com.eliasfb.bgn.model.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GameMapper {

  public static final String BACKEND_HOST = "http://192.168.0.25:8080";

  default List<GameDto> gameToGameDtoList(List<Game> games) {
    return games.stream().map(g -> this.gameToGameDto(g)).collect(Collectors.toList());
  }

  @Mapping(source = "location.name", target = "location")
  @Mapping(source = "victory.name", target = "victory")
  @Mapping(source = "theme.name", target = "theme")
  @Mapping(source = "averageScore", target = "avgScore")
  GameDto basicGameToGameDto(Game game);

  default GameDto gameToGameDto(Game game) {
    GameDto gameDto = this.basicGameToGameDto(game);
    gameDto.setImageUrl(
        game.getImages().stream()
            .map(g -> BACKEND_HOST + "/images/" + g.getName())
            .findFirst()
            .orElse(null));
    return gameDto;
  }

  @Mapping(source = "victory.name", target = "victory")
  @Mapping(source = "theme.name", target = "theme")
  @Mapping(source = "complexity.name", target = "complexity")
  @Mapping(source = "medium.name", target = "medium")
  @Mapping(source = "style.name", target = "style")
  @Mapping(source = "scores", target = "scoreInfo.scores")
  @Mapping(source = "averageScore", target = "scoreInfo.avgValue")
  GameDetailDto basicGameToGameDetailDto(Game game);

  default GameDetailDto gameToGameDetailDto(Game game) {
    GameDetailDto gameDetailDto = this.basicGameToGameDetailDto(game);
    gameDetailDto.setImageUrls(
        game.getImages().stream()
            .map(g -> BACKEND_HOST + "/images/" + g.getName())
            .collect(Collectors.toList()));
    return gameDetailDto;
  }

  @Mapping(source = "themeId", target = "theme.id")
  @Mapping(source = "complexityId", target = "complexity.id")
  @Mapping(source = "locationId", target = "location.id")
  @Mapping(source = "mediumId", target = "medium.id")
  @Mapping(source = "styleId", target = "style.id")
  @Mapping(source = "victoryId", target = "victory.id")
  Game createGameDtoToGame(CreateGameDto dto);
}
