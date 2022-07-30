package com.eliasfb.bgn.controller;

import com.eliasfb.bgn.openapi.api.ImportApi;
import com.eliasfb.bgn.openapi.model.ResponseDto;
import com.eliasfb.bgn.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

import static com.eliasfb.bgn.service.GameService.OK_CODE;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class ImportController implements ImportApi {

  @Autowired private ImportService service;

  @Override
  public ResponseEntity<ResponseDto> importAll() {
    try {
      String finalMessage = this.service.importPlayers().getMessage();
      finalMessage += " / " + this.service.importGames().getMessage();
      finalMessage += " / " + this.service.importPlays().getMessage();
      return ResponseEntity.ok(new ResponseDto().errorCode(OK_CODE).message(finalMessage));
    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.ok(new ResponseDto().errorCode(-1).message(e.toString()));
    }
  }
}
