package com.eliasfb.bgn.dto.challenge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
  private Integer id;
  private String createdDate;
  private String dueDate;
  private String name;
  private String value;
}
