package com.eliasfb.bgn.service;

import com.eliasfb.bgn.model.PlayPlayerRel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PlayerServiceTest {

  PlayerService playerService = new PlayerService();

  @Test
  public void getTotalWinPercentageTest() {
    // 50 percent
    List<PlayPlayerRel> playerPlays = new ArrayList<>();
    playerPlays.add(new PlayPlayerRel(true));
    playerPlays.add(new PlayPlayerRel(false));
    Double winPercentage = playerService.getWinPercentage(playerPlays);
    // 100 percent
    assertEquals(winPercentage, Double.valueOf(50));
    playerPlays = new ArrayList<>();
    playerPlays.add(new PlayPlayerRel(true));
    playerPlays.add(new PlayPlayerRel(true));
    winPercentage = playerService.getWinPercentage(playerPlays);
    assertEquals(winPercentage, Double.valueOf(100));
    // 0 percent
    playerPlays = new ArrayList<>();
    playerPlays.add(new PlayPlayerRel(false));
    playerPlays.add(new PlayPlayerRel(false));
    winPercentage = playerService.getWinPercentage(playerPlays);
    assertEquals(winPercentage, Double.valueOf(0));
    // 33.33 percent
    playerPlays = new ArrayList<>();
    playerPlays.add(new PlayPlayerRel(true));
    playerPlays.add(new PlayPlayerRel(false));
    playerPlays.add(new PlayPlayerRel(false));
    winPercentage = playerService.getWinPercentage(playerPlays);
    assertEquals(winPercentage, Double.valueOf(33.33));
  }
}
