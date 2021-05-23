package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.game.CreateGameDto;
import com.eliasfb.bgn.dto.game.GameDetailDto;
import com.eliasfb.bgn.dto.game.GameDto;
import com.eliasfb.bgn.dto.game.GameLocationUpdateDto;
import com.eliasfb.bgn.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/games"})
public class GameController {
  @Autowired private GameService service;

  @GetMapping
  public List<GameDto> findAll() {
    return this.service.findAll();
  }

  @GetMapping(path = {"/{id}"})
  public GameDetailDto findGame(@PathVariable("id") int id) {
    return this.service.findById(id);
  }

  @PostMapping
  public ResponseDto create(@RequestBody CreateGameDto game) {
    return this.service.create(game);
  }

  @PutMapping(path = {"/{id}/location"})
  public ResponseDto updateLocation(
      @PathVariable("id") int id, @RequestBody GameLocationUpdateDto game) {
    return this.service.updateLocation(id, game);
  }

  @PutMapping(path = {"/{id}/play"})
  public ResponseDto registerPlay(@PathVariable("id") int id) {
    return this.service.registerPlay(id);
  }

  @PutMapping(path = {"/{id}/score/{scoreId}/{value}"})
  public Double updateScoreValue(
      @PathVariable("id") int id,
      @PathVariable("scoreId") int scoreId,
      @PathVariable("value") int value) {
    return this.service.updateScoreValue(id, scoreId, value);
  }
}
