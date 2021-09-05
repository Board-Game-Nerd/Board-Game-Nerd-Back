package com.eliasfb.bgn.dto.play;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreatePlayDto {
  private Integer gameId;
  private List<CreatePlayPlayerDto> players;
}
