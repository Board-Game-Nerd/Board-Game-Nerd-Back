package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player_group")
@Data
@ToString(exclude = {"players"})
@EqualsAndHashCode(exclude = {"players"})
@NoArgsConstructor
public class PlayerGroup {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;
  @Column private String image;

  @OneToMany(
      mappedBy = "id.playerGroup",
      cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<PlayerGroupPlayerRel> players;
}
