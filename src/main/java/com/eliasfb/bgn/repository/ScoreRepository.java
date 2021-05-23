package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Score;
import org.springframework.data.repository.Repository;

public interface ScoreRepository extends Repository<Score, Integer> {

  Score findById(Integer id);

  Score save(Score score);
}
