package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "image")
@Data
@NoArgsConstructor
public class Image {
  @Id @Column private Integer id;
  @Column private Integer gameId;
  @Column private String name;
  @Column private Integer sequence;
}
