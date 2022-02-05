package com.eliasfb.bgn.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeGameRelId implements Serializable {

  private static final long serialVersionUID = 1L;

  @ManyToOne private Challenge challenge;

  @ManyToOne private Game game;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    ChallengeGameRelId that = (ChallengeGameRelId) o;
    return Objects.equals(challenge, that.challenge) && Objects.equals(game, that.game);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, challenge);
  }
}
