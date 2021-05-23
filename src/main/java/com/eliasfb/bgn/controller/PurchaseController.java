package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.purchase.CreatePurchaseDto;
import com.eliasfb.bgn.dto.purchase.PurchaseDto;
import com.eliasfb.bgn.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/purchases"})
public class PurchaseController {
  @Autowired private PurchaseService service;

  @GetMapping
  public List<PurchaseDto> findAll() {
    return this.service.findAll();
  }

  @PostMapping
  public ResponseDto createPurchase(@RequestBody CreatePurchaseDto purchase) {
    return this.service.createPurchase(purchase);
  }
}
