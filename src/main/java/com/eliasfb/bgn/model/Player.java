package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player")
@Data
@ToString(exclude = {"plays"})
@EqualsAndHashCode(exclude = {"plays"})
@NoArgsConstructor
public class Player {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;
  @Column private String image;

  @OneToMany(
      mappedBy = "id.player",
      cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<PlayPlayerRel> plays;

  public Player(Integer id) {
    this.id = id;
  }

  public Player(String name) {
    this.name = name;
    this.image = this.name + ".jpg";
  }
}
