package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Image;
import org.springframework.data.repository.Repository;

public interface ImageRepository extends Repository<Image, Integer> {

  Image findById(Integer id);

  Image save(Image image);
}
