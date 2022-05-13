package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer>, JpaSpecificationExecutor<Player> {
    Player findById(long id);

//    Player updatePlayer(long id, Player player);


    void deletePlayerById(long id);

}
