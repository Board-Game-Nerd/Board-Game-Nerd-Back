package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Game;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GameRepository extends Repository<Game, Integer> {

  Game findById(Integer id);

  List<Game> findAll();

  Game save(Game game);
}
