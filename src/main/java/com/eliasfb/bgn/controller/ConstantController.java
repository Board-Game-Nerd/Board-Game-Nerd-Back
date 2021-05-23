package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ConstantsDto;
import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.service.ConstantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/constants"})
public class ConstantController {

    @Autowired
    private ConstantService service;

    @GetMapping
    public ConstantsDto findLastBuyDate() {
        return this.service.findLastBuyDate();
    }

    @PostMapping
    public ResponseDto registerBuy() {
        return this.service.registerBuy();
    }
}
