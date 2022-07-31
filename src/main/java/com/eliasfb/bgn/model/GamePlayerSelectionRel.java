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
@Table(name = "game_play_configuration")
@AssociationOverrides({
  @AssociationOverride(name = "id.game", joinColumns = @JoinColumn(name = "game_id")),
  @AssociationOverride(
      name = "id.playerSelection",
      joinColumns = @JoinColumn(name = "game_play_player_selection_id"))
})
public class GamePlayerSelectionRel {
  @EmbeddedId private GamePlayerSelectionRelId id;

  @Column private Integer sequence;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GamePlayerSelectionRel that = (GamePlayerSelectionRel) o;
    return id != null ? Objects.equals(id, that.id) : false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
