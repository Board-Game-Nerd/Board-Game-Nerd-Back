package com.eliasfb.bgn.controller;

/*@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping({"/import"})*/
public class ImportController {

  /*@Autowired private ImportService service;

  @PostMapping()
  public ResponseDto importAll() {
    try {
      String finalMessage = this.service.importPlayers().getMessage();
      finalMessage += " / " + this.service.importGames().getMessage();
      finalMessage += " / " + this.service.importPlays().getMessage();
      return new ResponseDto().errorCode(OK_CODE).message(finalMessage);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto().errorCode(-1).message(e.toString());
    }
  }

  @PostMapping(path = {"/players"})
  public ResponseDto importPlayers() {
    try {
      return this.service.importPlayers();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto().errorCode(-1).message(e.toString());
    }
  }

  @PostMapping(path = {"/games"})
  public ResponseDto importGames() {
    try {
      return this.service.importGames();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto().errorCode(-1).message(e.toString());
    }
  }

  @PostMapping(path = {"/plays"})
  public ResponseDto importPlays() {
    try {
      return this.service.importPlays();
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseDto().errorCode(-1).message(e.toString());
    }
  }*/
}
