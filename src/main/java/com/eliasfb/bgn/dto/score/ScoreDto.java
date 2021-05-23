package com.eliasfb.bgn.dto.score;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScoreDto {
  private Integer id;
  private String name;
  private Integer value;
}
