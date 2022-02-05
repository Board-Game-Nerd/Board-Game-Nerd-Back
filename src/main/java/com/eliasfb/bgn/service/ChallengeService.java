package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.challenge.ChallengeDetailDto;
import com.eliasfb.bgn.dto.challenge.ChallengeDto;
import com.eliasfb.bgn.mapper.ChallengeMapper;
import com.eliasfb.bgn.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeService {
  @Autowired private ChallengeRepository repository;
  @Autowired private ChallengeMapper mapper;

  public List<ChallengeDto> findAll() {
    return this.mapper.challengeListToChallengeListDto(this.repository.findAll());
  }

  public ChallengeDetailDto findById(Integer id) {
    return this.mapper.challengeToChallengeDetailDto(this.repository.findById(id));
  }

  public List<Integer> findIds() {
    return this.repository.findAll().stream().map(c -> c.getId()).collect(Collectors.toList());
  }
}
