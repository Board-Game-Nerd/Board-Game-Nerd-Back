package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Constant;
import org.springframework.data.repository.Repository;

public interface ConstantsRepository extends Repository<Constant, Integer> {

    Constant findByIdentifier(String identifier);

    Constant save(Constant constant);
}
