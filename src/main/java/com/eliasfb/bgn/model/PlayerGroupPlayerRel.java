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
@Table(name = "player_group_player_rel")
@AssociationOverrides({
  @AssociationOverride(
      name = "id.playerGroup",
      joinColumns = @JoinColumn(name = "player_group_id")),
  @AssociationOverride(name = "id.player", joinColumns = @JoinColumn(name = "player_id"))
})
public class PlayerGroupPlayerRel {

  @EmbeddedId private PlayerGroupPlayerRelId id;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlayerGroupPlayerRel that = (PlayerGroupPlayerRel) o;
    return id != null ? Objects.equals(id, that.id) : false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
