package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.challenge.ChallengeDetailDto;
import com.eliasfb.bgn.dto.challenge.ChallengeDto;
import com.eliasfb.bgn.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/challenges"})
public class ChallengeController {
  @Autowired private ChallengeService service;

  @GetMapping
  public List<ChallengeDto> findAll() {
    return this.service.findAll();
  }

  @GetMapping(path = {"/{id}"})
  public ChallengeDetailDto findChallenge(@PathVariable("id") int id) {
    return this.service.findById(id);
  }

  @GetMapping(path = {"/ids"})
  public List<Integer> findIds() {
    return this.service.findIds();
  }
}