package com.eliasfb.bgn.dto.bgstats;

import lombok.Data;

import java.util.List;

@Data
public class BgStatsDto {
  private List<BgStatsGameDto> games;
  private List<BgStatsPlayerDto> players;
  private List<BgStatsPlayDto> plays;
}
