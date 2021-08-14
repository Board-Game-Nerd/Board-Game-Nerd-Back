package com.eliasfb.bgn.dto.game;

import com.eliasfb.bgn.dto.master.MasterDto;
import com.eliasfb.bgn.dto.score.ScoreInfoDto;
import lombok.Data;

import java.util.List;

@Data
public class GameDetailDto {
  private String name;
  private List<String> imageUrls;
  private String description;
  private Integer minPlayers;
  private Integer maxPlayers;
  private String theme;
  private String complexity;
  private MasterDto location;
  private String medium;
  private String style;
  private String victory;
  private Integer duration;
  private String lastPlayedDate;
  private ScoreInfoDto scoreInfo;
  private String rulesUrl;
}
