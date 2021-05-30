package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Theme;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ThemeRepository extends Repository<Theme, Integer> {
  List<Theme> findAll();

  List<Theme> findByOrderByNameAsc();
}
