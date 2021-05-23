package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Theme;
import com.eliasfb.bgn.model.Victory;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface VictoryRepository extends Repository<Victory, Integer> {
    List<Victory> findAll();
}
