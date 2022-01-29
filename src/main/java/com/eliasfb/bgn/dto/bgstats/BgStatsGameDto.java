package com.eliasfb.bgn.dto.bgstats;

import lombok.Data;

@Data
public class BgStatsGameDto {
  private String name;
  private Integer id;
  private Integer minPlayerCount;
  private Integer maxPlayerCount;
  private boolean cooperative;
  private boolean noPoints;
}
