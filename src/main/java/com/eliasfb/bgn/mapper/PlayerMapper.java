package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDetailDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.dto.player.group.PlayerGroupDto;
import com.eliasfb.bgn.model.Player;
import com.eliasfb.bgn.model.PlayerGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.eliasfb.bgn.mapper.GameMapper.BACKEND_HOST;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

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

  @Mapping(target = "players", ignore = true)
  PlayerGroupDto basicPlayerGroupToPlayerGroupDto(PlayerGroup playerGroup);

  default PlayerGroupDto playerGroupToPlayerGroupDto(PlayerGroup playerGroup) {
    PlayerGroupDto playerGroupDto = basicPlayerGroupToPlayerGroupDto(playerGroup);
    playerGroupDto.setImageUrl(BACKEND_HOST + "/images/players/groups/" + playerGroup.getImage());
    return playerGroupDto;
  }
}
