package com.eliasfb.bgn.dto.game;

import lombok.Data;

@Data
public class CreateGameDto {
  private String name;
  private String description;
  private Integer minPlayers;
  private Integer maxPlayers;
  private Integer themeId;
  private Integer complexityId;
  private Integer locationId;
  private Integer mediumId;
  private Integer styleId;
  private Integer victoryId;
  private Integer duration;
}
