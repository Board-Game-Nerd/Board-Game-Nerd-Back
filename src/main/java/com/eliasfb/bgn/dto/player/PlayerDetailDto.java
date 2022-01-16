package com.eliasfb.bgn.dto.player;

import com.eliasfb.bgn.dto.game.GameStatDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDetailDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private Integer numPlays;
  private Double totalWinPercentage;
  private Integer highestPlayScore;
  private GameStatDto favouriteGame;
  private GameStatDto highestWinRateGame;
  private GameStatDto latestPlayedGame;
}
