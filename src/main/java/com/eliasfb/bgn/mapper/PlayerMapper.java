package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDetailDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.model.Player;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.eliasfb.bgn.mapper.GameMapper.BACKEND_HOST;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

  default List<PlayerDto> playerListToPlayerDtoList(List<Player> players) {
    return players.stream().map(g -> this.playerToPlayerDto(g)).collect(Collectors.toList());
  }

  PlayerDto basicPlayerToPlayerDto(Player player);

  PlayerDetailDto basicPlayerToPlayerDetailDto(Player player);

  default PlayerDetailDto playerToPlayerDetailDto(Player player) {
    PlayerDetailDto playerDetailDto = basicPlayerToPlayerDetailDto(player);
    playerDetailDto.setImageUrl(BACKEND_HOST + "/images/players/" + player.getImage());
    return playerDetailDto;
  }

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
