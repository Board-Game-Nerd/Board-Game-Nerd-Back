package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Purchase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PurchaseRepository extends Repository<Purchase, Integer> {
  @Query("select p from Purchase p ORDER BY p.date DESC")
  List<Purchase> findAll();

  Purchase save(Purchase purchase);
}
