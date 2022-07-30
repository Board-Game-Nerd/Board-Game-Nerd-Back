package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.PlayersApi;
import com.eliasfb.bgn.openapi.model.*;
import com.eliasfb.bgn.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class PlayerController implements PlayersApi {
  @Autowired private PlayerService service;

  @Override
  public ResponseEntity<List<Integer>> getPlayerIds() {
    return ResponseEntity.ok(this.service.findIds());
  }

  @Override
  public ResponseEntity<List<PlayerDto>> getPlayers() {
    return ResponseEntity.ok(this.service.findAll());
  }

  @Override
  public ResponseEntity<List<PlayerGroupDto>> getPlayerGroups() {
    return ResponseEntity.ok(this.service.findAllPlayerGroups());
  }

  @Override
  public ResponseEntity<PlayerDetailDto> getPlayerById(Integer playerId) {
    try {
      return ResponseEntity.ok(this.service.findById(playerId));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public ResponseEntity<ResponseDto> createPlayer(CreatePlayerDto createPlayer) {
    return ResponseEntity.ok(this.service.create(createPlayer));
  }
}
