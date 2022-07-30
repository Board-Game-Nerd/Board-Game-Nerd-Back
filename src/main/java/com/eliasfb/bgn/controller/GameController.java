package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.GamesApi;
import com.eliasfb.bgn.openapi.model.*;
import com.eliasfb.bgn.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class GameController implements GamesApi {
  @Autowired private GameService service;

  @Override
  public ResponseEntity<List<GameDto>> getGames() {
    return ResponseEntity.ok(this.service.findAll());
  }

  @Override
  public ResponseEntity<List<Integer>> getGameIds() {
    return ResponseEntity.ok(this.service.findIds());
  }

  @Override
  public ResponseEntity<GameDetailDto> getGameById(Integer gameId) {
    return ResponseEntity.ok(this.service.findById(gameId));
  }

  @Override
  public ResponseEntity<ResponseDto> createGame(CreateGameDto createGame) {
    return ResponseEntity.ok(this.service.create(createGame));
  }

  @Override
  public ResponseEntity<GameDetailDto> updateGameFavoriteStatus(Integer gameId) {
    return ResponseEntity.ok(this.service.updateFavoriteStatus(gameId));
  }

  @Override
  public ResponseEntity<ResponseDto> updateLocation(
      Integer gameId, GameLocationUpdateDto gameUpdateLocation) {
    return ResponseEntity.ok(this.service.updateLocation(gameId, gameUpdateLocation));
  }

  @Override
  public ResponseEntity<Double> updateScoreValue(
      Integer gameId, Integer scoreId, Integer scoreValue) {
    return ResponseEntity.ok(this.service.updateScoreValue(gameId, scoreId, scoreValue));
  }
}
