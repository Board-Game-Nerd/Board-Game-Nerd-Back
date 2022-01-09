package com.eliasfb.bgn.dto.bgstats;

import lombok.Data;

import java.util.List;

@Data
public class BgStatsPlayDto {
  private String playDate;
  private Integer gameRefId;
  private String gameName;
  private List<BgStatsPlayerScoreDto> playerScores;
}
