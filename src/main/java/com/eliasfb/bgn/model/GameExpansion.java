package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "game_expansion")
@Data
@NoArgsConstructor
public class GameExpansion {

  @EmbeddedId private GameExpansionId id;

  @Column private Boolean owned;

  @Column private Integer sequence;
}
