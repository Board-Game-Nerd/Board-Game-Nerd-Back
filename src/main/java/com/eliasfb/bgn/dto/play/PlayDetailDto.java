package com.eliasfb.bgn.dto.play;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayDetailDto {
  private int id;
  private String date;
  private List<PlayPlayerDto> players;
  private BasicGameDto game;
}
