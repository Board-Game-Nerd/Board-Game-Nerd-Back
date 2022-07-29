package com.eliasfb.bgn.dto.game;

import com.eliasfb.bgn.dto.master.MasterDto;
import com.eliasfb.bgn.dto.score.ScoreInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class GameDetailDto {
  private Integer id;
  private String name;
  private List<ImageDto> images;
  private String description;
  private String auxiliarDescription;
  private Integer minPlayers;
  private Integer maxPlayers;
  private String theme;
  private String complexity;
  private MasterDto location;
  private String medium;
  private String style;
  private String victory;
  private Integer duration;
  private ScoreInfoDto scoreInfo;
  private String rulesUrl;
  private boolean isFavorite;
  private GameExpansionsDto expansions;
  private Integer numPlays;
}
