package com.eliasfb.bgn.dto.player.group;

import com.eliasfb.bgn.dto.player.PlayerDto;
import lombok.Data;

import java.util.List;

@Data
public class PlayerGroupDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private List<PlayerDto> players;
}
