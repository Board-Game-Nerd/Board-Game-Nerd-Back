package com.eliasfb.bgn.dto.player;

import lombok.Data;

@Data
public class PlayerDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private Integer numPlays;
}
