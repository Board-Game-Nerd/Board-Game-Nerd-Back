package com.eliasfb.bgn.dto.game;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImageDto {
  private String src;
  private String thumb;

  public ImageDto(String url) {
    this.src = url;
    this.thumb = url;
  }
}
