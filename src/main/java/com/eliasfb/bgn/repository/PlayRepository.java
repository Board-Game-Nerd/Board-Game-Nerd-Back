package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Play;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayRepository extends Repository<Play, Integer> {

  List<Play> findAll();

  Play findById(Integer id);

  List<Play> findAll(Sort sort);

  Play save(Play play);
}
