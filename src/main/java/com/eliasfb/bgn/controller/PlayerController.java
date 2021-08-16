package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.player.CreatePlayerDto;
import com.eliasfb.bgn.dto.player.PlayerDto;
import com.eliasfb.bgn.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/players"})
public class PlayerController {
  @Autowired private PlayerService service;

  @GetMapping
  public List<PlayerDto> findAll() {
    return this.service.findAll();
  }

  @PostMapping
  public ResponseDto create(@RequestBody CreatePlayerDto player) {
    return this.service.create(player);
  }
}
