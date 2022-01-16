package com.eliasfb.bgn.dto.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class GameStatDto {
  private String gameName;
  private String gameImageUrl;
  private String value;
  private String secondValue;

  public GameStatDto(String gameName, String gameImageUrl, String value) {
    this.gameName = gameName;
    this.gameImageUrl = gameImageUrl;
    this.value = value;
  }
}
