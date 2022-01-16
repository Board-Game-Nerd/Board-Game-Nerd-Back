package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.play.CreatePlayDto;
import com.eliasfb.bgn.dto.play.PlayDto;
import com.eliasfb.bgn.mapper.PlayMapper;
import com.eliasfb.bgn.model.Play;
import com.eliasfb.bgn.repository.GameRepository;
import com.eliasfb.bgn.repository.PlayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
public class PlayService {
  @Autowired private GameRepository gameRepository;
  @Autowired private PlayRepository repository;
  @Autowired private PlayMapper mapper;

  public List<PlayDto> findAll() {
    return this.mapper.playsToPlaysDto(this.repository.findAll());
  }

  @Transactional
  public ResponseDto create(CreatePlayDto playDto) {
    ResponseDto responseDto = new ResponseDto(ResponseDto.OK_CODE, "Play created successfully");
    Play playToCreate = this.mapper.createPlayToPlay(playDto);
    playToCreate.setGame(this.gameRepository.findById(playDto.getGameId()));
    playToCreate.setDate(new Date(System.currentTimeMillis()));
    this.repository.save(playToCreate);
    return responseDto;
  }
}
