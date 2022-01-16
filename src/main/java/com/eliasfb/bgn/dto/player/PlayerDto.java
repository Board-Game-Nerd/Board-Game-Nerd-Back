package com.eliasfb.bgn.dto.player;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private Integer numPlays;
}
