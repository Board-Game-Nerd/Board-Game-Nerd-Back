package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.model.Player;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
  public static final String BACKEND_HOST = "http://192.168.0.25:8080";

  default List<PlayerDto> playerListToPlayerDtoList(List<Player> players) {
    return players.stream().map(g -> this.playerToPlayerDto(g)).collect(Collectors.toList());
  }

  PlayerDto basicPlayerToPlayerDto(Player player);

  default PlayerDto playerToPlayerDto(Player player) {
    PlayerDto playerDto = basicPlayerToPlayerDto(player);
    playerDto.setImageUrl(BACKEND_HOST + "/images/players/" + player.getImage());
    return playerDto;
  }

  Player basicCreatePlayerToPlayer(CreatePlayerDto playerDto);

  default Player createPlayerToPlayer(CreatePlayerDto playerDto) {
    Player player = basicCreatePlayerToPlayer(playerDto);
    player.setImage(playerDto.getName() + ".jpg");
    return player;
  }
}
