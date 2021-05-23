package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Location;
import com.eliasfb.bgn.model.Theme;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface LocationRepository extends Repository<Location, Integer> {
    List<Location> findAll();

    Location findById(Integer id);
}
