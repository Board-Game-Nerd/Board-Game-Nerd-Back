package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "player")
@Data
@ToString(exclude = {})
@EqualsAndHashCode(exclude = {})
@NoArgsConstructor
public class Player {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;
  @Column private String image;

  public Player(Integer id) {
    this.id = id;
  }
}
