package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.master.MastersDto;
import com.eliasfb.bgn.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/masters"})
public class MasterController {
  @Autowired private MasterService service;

  @GetMapping
  public MastersDto findAll() {
    return this.service.findAll();
  }
}
