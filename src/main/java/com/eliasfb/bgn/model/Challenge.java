package com.eliasfb.bgn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "challenge")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Challenge {
  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column private String name;

  @Column private String type;

  @Column private Date createdDate;

  @Column private Date dueDate;

  @Column private Integer value;

  @OneToMany(
      mappedBy = "id.challenge",
      cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<ChallengeGameRel> gameStats;
}
