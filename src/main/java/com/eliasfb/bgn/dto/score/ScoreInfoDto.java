package com.eliasfb.bgn.dto.score;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreInfoDto {
  private List<ScoreDto> scores;
  private Double avgValue;
}
