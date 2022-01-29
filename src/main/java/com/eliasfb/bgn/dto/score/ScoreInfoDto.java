package com.eliasfb.bgn.dto.score;

import lombok.Data;

import java.util.List;

@Data
public class ScoreInfoDto {
  private List<ScoreDto> scores;
  private Double avgValue;
}
