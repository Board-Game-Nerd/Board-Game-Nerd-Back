package com.eliasfb.bgn.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "complexity")
@Data
@NoArgsConstructor
public class Complexity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column private String name;
}
