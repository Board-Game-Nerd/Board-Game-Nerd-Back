package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "game_play_player_selection")
@Data
@NoArgsConstructor
public class GamePlayPlayerSelection {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;

  @Column private String subselection;

  @Column private boolean isMultipleSelection;

  @Column private boolean isSubselectionMultipleSelection;
}
