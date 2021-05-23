package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Medium;
import com.eliasfb.bgn.model.Theme;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface MediumRepository extends Repository<Medium, Integer> {
    List<Medium> findAll();
}
