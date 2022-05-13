package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.ServletRequest;
import java.util.List;

public interface PlayerService {


    Page<Player> findByCriteria(ServletRequest request, Pageable pageable);

    Player updCreatePlayer(Player player);

    Player updUpdatePlayer(long id, Player player);

    Player getPlayer(long id);

    void deletePlayerById(long id);
}
