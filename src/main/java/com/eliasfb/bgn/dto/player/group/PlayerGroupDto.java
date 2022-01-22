package com.eliasfb.bgn.dto.player.group;

import com.eliasfb.bgn.dto.player.PlayerDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerGroupDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private List<PlayerDto> players;
}
