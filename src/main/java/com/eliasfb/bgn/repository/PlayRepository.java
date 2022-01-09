package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Play;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayRepository extends Repository<Play, Integer> {

  List<Play> findAll();

  Play save(Play play);
}
