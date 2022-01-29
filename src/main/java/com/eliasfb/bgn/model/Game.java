package com.eliasfb.bgn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "game")
@Data
@ToString(exclude = {"theme", "complexity", "location", "medium", "style", "victory", "plays"})
@EqualsAndHashCode(
    exclude = {"theme", "complexity", "location", "medium", "style", "victory", "plays"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable, Comparable {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;
  @Column private String officialName;
  @Column private String description;
  @Column private String auxiliarDescription;
  @Column private Integer minPlayers;
  @Column private Integer maxPlayers;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Theme theme;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Complexity complexity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Location location;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Medium medium;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Style style;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private Victory victory;

  @OneToMany(mappedBy = "gameId")
  @JsonIgnore
  private List<Score> scores;

  @OneToMany(mappedBy = "gameId")
  @JsonIgnore
  private List<Image> images;

  @Column private Integer duration;

  @Column private String featuresDisabled;

  @Column private boolean isFavorite;

  @Column private boolean owned;

  @Column private boolean scorable;

  @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonIgnore
  private List<Play> plays;

  @OneToMany(mappedBy = "id.gameId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @OrderBy("sequence ASC")
  @JsonIgnore
  private List<GameExpansion> expansions;

  public Double getAverageScore() {
    return this.scores.stream().mapToInt(Score::getValue).average().orElse(0);
  }

  @Override
  public int compareTo(Object o) {
    if (o instanceof Game) {
      return this.id.compareTo(((Game) o).id);
    }
    return -1;
  }
}
