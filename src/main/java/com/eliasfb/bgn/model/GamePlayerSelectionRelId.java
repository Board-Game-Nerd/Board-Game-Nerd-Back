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
public class GamePlayerSelectionRelId implements Serializable {
  private static final long serialVersionUID = 1L;

  @ManyToOne private Game game;

  @ManyToOne private GamePlayPlayerSelection playerSelection;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    GamePlayerSelectionRelId that = (GamePlayerSelectionRelId) o;
    return Objects.equals(playerSelection, that.playerSelection) && Objects.equals(game, that.game);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, playerSelection);
  }
}
