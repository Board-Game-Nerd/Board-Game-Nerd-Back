package com.eliasfb.bgn.dto.game;

import com.eliasfb.bgn.dto.master.MasterDto;
import lombok.Data;

import java.util.List;

@Data
public class GameDto {
  private Integer id;
  private String name;
  private String imageUrl;
  private String location;
  private MasterDto complexity;
  private Integer minPlayers;
  private Integer maxPlayers;
  private String victory;
  private String theme;
  private String medium;
  private Integer duration;
  private Double avgScore;
  private List<String> featuresDisabled;
  private boolean isFavorite;
  private Integer numPlays;
  private List<String> playedBy;
}
