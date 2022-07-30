package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.ChallengesApi;
import com.eliasfb.bgn.openapi.model.ChallengeDetailDto;
import com.eliasfb.bgn.openapi.model.ChallengeDto;
import com.eliasfb.bgn.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChallengeController implements ChallengesApi {
  @Autowired private ChallengeService service;

  @Override
  public ResponseEntity<ChallengeDetailDto> getChallengeById(Integer challengeId) {
    return ResponseEntity.ok(this.service.findById(challengeId));
  }

  @Override
  public ResponseEntity<List<Integer>> getChallengeIds() {
    return ResponseEntity.ok(this.service.findIds());
  }

  @Override
  public ResponseEntity<List<ChallengeDto>> getChallenges() {
    return ResponseEntity.ok(this.service.findAll());
  }
}
