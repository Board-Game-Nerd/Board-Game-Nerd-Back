package com.eliasfb.bgn.dto.play;

import com.eliasfb.bgn.dto.player.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayPlayerDto {
  private PlayerDto player;
  private Integer score;
  private boolean winner;
}
