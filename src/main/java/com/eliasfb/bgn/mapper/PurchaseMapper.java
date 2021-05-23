package com.eliasfb.bgn.mapper;

import com.eliasfb.bgn.dto.purchase.CreatePurchaseDto;
import com.eliasfb.bgn.dto.purchase.PurchaseDto;
import com.eliasfb.bgn.model.Purchase;
import org.mapstruct.Mapper;

import java.util.List;

import static com.eliasfb.bgn.service.DateService.SIMPLE_DATE_FORMAT;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

  public default PurchaseDto toDto(Purchase purchase) {
    PurchaseDto dto = new PurchaseDto();
    dto.setName(purchase.getName());
    dto.setAmount(purchase.getAmount());
    dto.setDate(SIMPLE_DATE_FORMAT.format(purchase.getDate()));
    return dto;
  }

  List<PurchaseDto> toDtoList(List<Purchase> purchase);

  Purchase createPurchaseToEntity(CreatePurchaseDto dto);
}
