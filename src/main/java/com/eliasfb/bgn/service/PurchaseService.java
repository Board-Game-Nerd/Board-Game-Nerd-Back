package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.purchase.CreatePurchaseDto;
import com.eliasfb.bgn.dto.purchase.PurchaseDto;
import com.eliasfb.bgn.mapper.PurchaseMapper;
import com.eliasfb.bgn.model.Purchase;
import com.eliasfb.bgn.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;

@Service
public class PurchaseService {
  @Autowired private PurchaseRepository repository;
  @Autowired private PurchaseMapper mapper;

  public List<PurchaseDto> findAll() {
    return this.mapper.toDtoList(this.repository.findAll());
  }

  @Transactional
  public ResponseDto createPurchase(CreatePurchaseDto dto) {
    ResponseDto responseDto =
        new ResponseDto(ResponseDto.OK_CODE, "Purchase registered successfully");
    Purchase purchase = this.mapper.createPurchaseToEntity(dto);
    purchase.setDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
    this.repository.save(purchase);
    return responseDto;
  }
}
