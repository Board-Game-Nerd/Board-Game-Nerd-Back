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
@Table(name = "challenge_game_rel")
@AssociationOverrides({
  @AssociationOverride(name = "id.challenge", joinColumns = @JoinColumn(name = "challenge_id")),
  @AssociationOverride(name = "id.game", joinColumns = @JoinColumn(name = "game_id"))
})
public class ChallengeGameRel {

  @EmbeddedId private ChallengeGameRelId id;

  @Column private Integer value;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ChallengeGameRel that = (ChallengeGameRel) o;
    return id != null ? Objects.equals(id, that.id) : false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
