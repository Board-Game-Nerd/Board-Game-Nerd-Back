package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.PlaysApi;
import com.eliasfb.bgn.openapi.model.PlayDetailDto;
import com.eliasfb.bgn.openapi.model.PlayDto;
import com.eliasfb.bgn.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class PlayController implements PlaysApi {
  @Autowired private PlayService service;

  /*@GetMapping(path = {"/ids"})
  public List<Integer> findIds() {
    return this.service.findIds();
  }*/

  @Override
  public ResponseEntity<List<PlayDto>> getPlays() {
    return ResponseEntity.ok(this.service.findAll());
  }

  @Override
  public ResponseEntity<PlayDetailDto> getPlayById(Integer playId) {
    return ResponseEntity.ok(this.service.findById(playId));
  }

  // TODO
  /*@PostMapping
  public ResponseDto create(@RequestBody CreatePlayDto play) {
    return this.service.create(play);
  }*/
}
