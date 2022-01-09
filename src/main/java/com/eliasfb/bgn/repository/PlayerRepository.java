package com.eliasfb.bgn.repository;

import com.eliasfb.bgn.model.Player;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface PlayerRepository extends Repository<Player, Integer> {

  Player findByName(String name);

  List<Player> findAll();

  void save(Player player);
}
