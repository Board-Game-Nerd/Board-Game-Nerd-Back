package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.GamesApi;
import com.eliasfb.bgn.openapi.model.GameDetailDto;
import com.eliasfb.bgn.openapi.model.GameDto;
import com.eliasfb.bgn.openapi.model.InlineObjectDto;
import com.eliasfb.bgn.openapi.model.ResponseDto;
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

  /*@GetMapping(path = {"/ids"})
  public List<Integer> findIds() {
    return this.service.findIds();
  }*/

  @Override
  public ResponseEntity<GameDetailDto> getGameById(Integer gameId) {
    return ResponseEntity.ok(this.service.findById(gameId));
  }

  @Override
  public ResponseEntity<ResponseDto> createGame(InlineObjectDto createGame) {
    return ResponseEntity.ok(this.service.create(createGame));
  }

  /*@PostMapping(path = {"/{id}/favorite"})
  public GameDetailDto updateGameFavoriteStatus(@PathVariable("id") int id) {
    return this.service.updateFavoriteStatus(id);
  }

  @PutMapping(path = {"/{id}/location"})
  public ResponseDto updateLocation(
      @PathVariable("id") int id, @RequestBody GameLocationUpdateDto game) {
    return this.service.updateLocation(id, game);
  }

  @PutMapping(path = {"/{id}/score/{scoreId}/{value}"})
  public Double updateScoreValue(
      @PathVariable("id") int id,
      @PathVariable("scoreId") int scoreId,
      @PathVariable("value") int value) {
    return this.service.updateScoreValue(id, scoreId, value);
  }*/
}
