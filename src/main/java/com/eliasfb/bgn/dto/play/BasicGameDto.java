package com.eliasfb.bgn.dto.play;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicGameDto {
  private String name;
  private String imageUrl;
}