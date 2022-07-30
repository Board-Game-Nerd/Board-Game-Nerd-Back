package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.PlayersApi;
import com.eliasfb.bgn.openapi.model.PlayerDetailDto;
import com.eliasfb.bgn.openapi.model.PlayerDto;
import com.eliasfb.bgn.openapi.model.PlayerGroupDto;
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

  /*@GetMapping(path = {"/ids"})
  public List<Integer> findIds() {
    return this.service.findIds();
  }*/

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

  /*@PostMapping
  public ResponseDto create(@RequestBody CreatePlayerDto player) {
    return this.service.create(player);
  }*/
}
