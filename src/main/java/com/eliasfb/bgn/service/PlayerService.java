package com.eliasfb.bgn.service;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.mapper.PlayerMapper;
import com.eliasfb.bgn.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlayerService {
  @Autowired private PlayerRepository repository;
  @Autowired private PlayerMapper mapper;

  public List<PlayerDto> findAll() {
    return this.mapper.playerListToPlayerDtoList(this.repository.findAll());
  }

  @Transactional
  public ResponseDto create(CreatePlayerDto playerDto) {
    ResponseDto responseDto = new ResponseDto(ResponseDto.OK_CODE, "Player created successfully");
    this.repository.save(this.mapper.createPlayerToPlayer(playerDto));
    return responseDto;
  }
}
