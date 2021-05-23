package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Style;
import com.eliasfb.bgn.model.Theme;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface StyleRepository extends Repository<Style, Integer> {
    List<Style> findAll();
}
