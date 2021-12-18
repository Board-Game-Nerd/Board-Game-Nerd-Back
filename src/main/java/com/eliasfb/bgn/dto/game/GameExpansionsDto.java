package com.eliasfb.bgn.dto.game;

import lombok.Data;

import java.util.List;

@Data
public class GameExpansionsDto {
  private Integer percentageOwned;
  List<GameExpansionDto> content;
}
