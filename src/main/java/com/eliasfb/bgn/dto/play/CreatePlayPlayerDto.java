package com.eliasfb.bgn.dto.play;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePlayPlayerDto {
  private int playerId;
  private int score;
  private boolean isWinner;
}
