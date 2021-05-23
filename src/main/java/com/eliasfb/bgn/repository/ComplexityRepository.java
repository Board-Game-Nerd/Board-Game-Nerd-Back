package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Complexity;
import com.eliasfb.bgn.model.Theme;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ComplexityRepository extends Repository<Complexity, Integer> {
    List<Complexity> findAll();
}
