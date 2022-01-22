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
public class PlayerGroupPlayerRelId implements Serializable {

  private static final long serialVersionUID = 1L;

  @ManyToOne private Player player;

  @ManyToOne private PlayerGroup playerGroup;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    PlayerGroupPlayerRelId that = (PlayerGroupPlayerRelId) o;
    return Objects.equals(player, that.player) && Objects.equals(playerGroup, that.playerGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerGroup, player);
  }
}
