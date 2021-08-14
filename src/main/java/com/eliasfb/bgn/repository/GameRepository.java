package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface GameRepository extends Repository<Game, Integer> {

  Game findById(Integer id);

  List<Game> findAll();

  Game save(Game game);

  @Modifying
  @Query("UPDATE Game g SET g.isFavorite=IF(g.isFavorite, 0, 1) where g.id=?1")
  @Transactional
  void setFavorite(Integer id);
}
