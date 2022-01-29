package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.dto.ResponseDto;
import com.eliasfb.bgn.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/import"})
public class ImportController {

  @Autowired private ImportService service;

  @PostMapping()
  public ResponseDto importAll() {
    try {
      String finalMessage = this.service.importPlayers().getMessage();
      finalMessage += " / " + this.service.importGames().getMessage();
      finalMessage += " / " + this.service.importPlays().getMessage();
      return new ResponseDto(ResponseDto.OK_CODE, finalMessage);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto(-1, e.toString());
    }
  }

  @PostMapping(path = {"/players"})
  public ResponseDto importPlayers() {
    try {
      return this.service.importPlayers();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto(-1, e.toString());
    }
  }

  @PostMapping(path = {"/games"})
  public ResponseDto importGames() {
    try {
      return this.service.importGames();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto(-1, e.toString());
    }
  }

  @PostMapping(path = {"/plays"})
  public ResponseDto importPlays() {
    try {
      return this.service.importPlays();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto(-1, e.toString());
    }
  }
}
