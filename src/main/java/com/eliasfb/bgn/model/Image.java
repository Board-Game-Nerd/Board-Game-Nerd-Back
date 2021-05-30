package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
public class Image {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private Integer gameId;
  @Column private String name;
  @Column private Integer sequence;

  public Image(Integer gameId, String name, Integer sequence) {
    this.gameId = gameId;
    this.name = name;
    this.sequence = sequence;
  }
}
