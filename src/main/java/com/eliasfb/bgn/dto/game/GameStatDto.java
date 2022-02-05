package com.eliasfb.bgn.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStatDto {
  private Integer gameId;
  private String gameName;
  private String gameImageUrl;
  private String value;
  private String secondValue;

  public GameStatDto(Integer gameId, String gameName, String gameImageUrl, String value) {
    this.gameId = gameId;
    this.gameName = gameName;
    this.gameImageUrl = gameImageUrl;
    this.value = value;
  }
}
