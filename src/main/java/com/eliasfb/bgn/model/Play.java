package com.eliasfb.bgn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "play")
@Data
@ToString(exclude = {"game"})
@EqualsAndHashCode(exclude = {"game"})
@NoArgsConstructor
public class Play {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "game_id")
  @JsonIgnore
  private Game game;

  @OneToMany(
      mappedBy = "id.play",
      cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<PlayPlayerRel> players;

  public Play(String date, Game game) {
    this.date = date;
    this.game = game;
  }
}
