package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.dto.play.CreatePlayDto;
import com.eliasfb.bgn.dto.play.PlayDetailDto;
import com.eliasfb.bgn.dto.play.PlayDto;
import com.eliasfb.bgn.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/plays"})
public class PlayController {
  @Autowired private PlayService service;

  @GetMapping(path = {"/ids"})
  public List<Integer> findIds() {
    return this.service.findIds();
  }

  @GetMapping
  public List<PlayDto> findAll() {
    return this.service.findAll();
  }

  @GetMapping(path = {"/{id}"})
  public PlayDetailDto findById(@PathVariable("id") int id) {
    return this.service.findById(id);
  }

  @PostMapping
  public ResponseDto create(@RequestBody CreatePlayDto play) {
    return this.service.create(play);
  }
}
