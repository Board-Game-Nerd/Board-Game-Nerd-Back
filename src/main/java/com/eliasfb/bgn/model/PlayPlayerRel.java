package com.eliasfb.bgn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playplayerrel")
@AssociationOverrides({
  @AssociationOverride(name = "id.play", joinColumns = @JoinColumn(name = "play_id")),
  @AssociationOverride(name = "id.player", joinColumns = @JoinColumn(name = "player_id"))
})
public class PlayPlayerRel {

  @EmbeddedId private PlayPlayerRelId id;

  private int score;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    PlayPlayerRel that = (PlayPlayerRel) o;
    return id != null ? Objects.equals(id, that.id) && Objects.equals(score, that.score) : false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, score);
  }
}
