package com.eliasfb.bgn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "play")
@Data
@ToString(exclude = {"game", "players"})
@EqualsAndHashCode(exclude = {"game", "players"})
@NoArgsConstructor
public class Play {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private Date date;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id")
  @JsonIgnore
  private Game game;

  @OneToMany(
      mappedBy = "id.play",
      cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<PlayPlayerRel> players;

  public Play(Date date, Game game) {
    this.date = date;
    this.game = game;
  }
}
