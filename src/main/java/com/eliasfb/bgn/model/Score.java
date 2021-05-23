package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "score")
@Data
@NoArgsConstructor
public class Score {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private Integer gameId;
  @Column private String name;
  @Column private Integer value;

  public Score(Integer gameId, String name, Integer value) {
    this.gameId = gameId;
    this.name = name;
    this.value = value;
  }
}
