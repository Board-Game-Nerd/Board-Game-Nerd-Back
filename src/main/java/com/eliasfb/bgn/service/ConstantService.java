package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ConstantsDto;
import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.model.Constant;
import com.eliasfb.bgn.repository.ConstantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.eliasfb.bgn.service.DateService.DATE_FORMAT;

@Service
public class ConstantService {

  @Autowired private ConstantsRepository constantsRepository;

  private final String LAST_BUY_IDENTIFIER = "LAST_BUY_DATE";

  public ConstantsDto findLastBuyDate() {
    String lastBuy = this.constantsRepository.findByIdentifier(LAST_BUY_IDENTIFIER).getValue();
    LocalDate lastBuyDate = LocalDate.parse(lastBuy, DATE_FORMAT);
    LocalDate nowDate = LocalDate.now();
    Long numDays = ChronoUnit.DAYS.between(lastBuyDate, nowDate);
    return new ConstantsDto(lastBuy, numDays);
  }

  @Transactional
  public ResponseDto registerBuy() {
    Constant constant = this.constantsRepository.findByIdentifier(LAST_BUY_IDENTIFIER);
    String now = LocalDate.now().format(DATE_FORMAT);
    constant.setValue(now);
    this.constantsRepository.save(constant);
    return new ResponseDto(ResponseDto.OK_CODE, "Purchase registered correctly");
  }
}
