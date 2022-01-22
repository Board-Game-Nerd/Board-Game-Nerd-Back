package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.PlayerGroup;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayerGroupRepository extends Repository<PlayerGroup, Integer> {
  List<PlayerGroup> findAll();
}
