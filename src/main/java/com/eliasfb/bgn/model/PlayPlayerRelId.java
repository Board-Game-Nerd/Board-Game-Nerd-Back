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
public class PlayPlayerRelId implements Serializable {

  private static final long serialVersionUID = 1L;

  @ManyToOne private Play play;

  @ManyToOne private Player player;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    PlayPlayerRelId that = (PlayPlayerRelId) o;
    return Objects.equals(play, that.play) && Objects.equals(player, that.player);
  }

  @Override
  public int hashCode() {
    return Objects.hash(play, player);
  }
}
