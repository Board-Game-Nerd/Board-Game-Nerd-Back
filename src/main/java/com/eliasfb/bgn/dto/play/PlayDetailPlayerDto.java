package com.eliasfb.bgn.dto.play;

import com.eliasfb.bgn.dto.player.PlayerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayDetailPlayerDto {
  private PlayerDto player;
  private Integer score;
  private boolean winner;
  private Double winPercentage;
  private Integer maxScore;
  private Integer avgScore;

  public PlayDetailPlayerDto(PlayerDto player, Integer score, boolean winner) {
    this.player = player;
    this.score = score;
    this.winner = winner;
  }
}
