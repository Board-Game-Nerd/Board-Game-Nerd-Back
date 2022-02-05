package com.eliasfb.bgn.dto.challenge;

import com.eliasfb.bgn.dto.game.GameStatDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDetailDto {
  private Integer id;
  private String createdDate;
  private String dueDate;
  private String name;
  private String value;
  private List<GameStatDto> gameStats;
}
