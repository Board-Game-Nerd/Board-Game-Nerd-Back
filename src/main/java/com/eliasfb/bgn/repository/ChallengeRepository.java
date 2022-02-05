package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Challenge;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ChallengeRepository extends Repository<Challenge, Integer> {

  Challenge findById(Integer id);

  List<Challenge> findAll();
}
