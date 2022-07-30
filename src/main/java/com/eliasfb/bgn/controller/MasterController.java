package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.MastersApi;
import com.eliasfb.bgn.openapi.model.MastersDto;
import com.eliasfb.bgn.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class MasterController implements MastersApi {
  @Autowired private MasterService service;

  @Override
  public ResponseEntity<MastersDto> getMasters() {
    return ResponseEntity.ok(this.service.findAll());
  }
}
