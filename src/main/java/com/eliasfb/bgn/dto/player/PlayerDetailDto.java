package com.eliasfb.bgn.dto.player;

import com.eliasfb.bgn.dto.game.GameStatDto;
import lombok.Data;

@Data
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
