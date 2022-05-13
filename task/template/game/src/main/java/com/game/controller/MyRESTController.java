package com.game.controller;

import com.game.entity.Player;
import com.game.exception_handling.IdIsNotValidException;
import com.game.exception_handling.NoSuchPlayerException;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.*;

@RestController
@RequestMapping("/rest")
public class MyRESTController {
    @Autowired
    private PlayerService playerService;

//    Integer playersCount = 0;

    @GetMapping("/players")
    public /*List<Player>*/ ResponseEntity<List<Player>> getAllPlayers(ServletRequest request) {
//        new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
//    public List<Player> getAllPlayers(@RequestParam(value = "order", defaultValue = "id") PlayerOrder order) {
        Map<String, String[]> paramMap = request.getParameterMap();
        PlayerOrder order = PlayerOrder.ID;
        List<Player> playerList;
        if (paramMap.containsKey("order")) {
            String[] orderParams = paramMap.get("order");
            order = PlayerOrder.valueOf(orderParams[0]);
        }
        Integer pageNumber = 0;
        Integer pageSize = 3;
        if (paramMap.containsKey("pageNumber")) {
            String[] pageNumberParams = paramMap.get("pageNumber");
            pageNumber = Integer.parseInt(pageNumberParams[0]);
        }
        if (paramMap.containsKey("pageSize")) {
            String[] pageSizeParams = paramMap.get("pageSize");
            pageSize = Integer.parseInt(pageSizeParams[0]);
        }

//        List<Player> playerList1 = new ArrayList<>();
        Pageable paging; /*= PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));*/
        Page<Player> pages;

        if (order == PlayerOrder.ID) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
            pages = playerService.findByCriteria(request, paging);
        } else if (order == PlayerOrder.EXPERIENCE) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "experience"));
            pages = playerService.findByCriteria(request, paging);
        } else if (order == PlayerOrder.NAME) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));
            pages = playerService.findByCriteria(request, paging);
        } else if (order == PlayerOrder.BIRTHDAY) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "birthday"));
            pages = playerService.findByCriteria(request, paging);
        } else if (order == PlayerOrder.LEVEL) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "level"));
            pages = playerService.findByCriteria(request, paging);
        } else {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "id"));
            pages = playerService.findByCriteria(request, paging);
        }

//        playersCount = Math.toIntExact(pages.getTotalElements());
        return new ResponseEntity<>(pages.getContent(), HttpStatus.OK);
    }

    @GetMapping("/players/{id}")
    public Player getPlayer(@PathVariable long id) {
        if (id < 1) {
            throw new IdIsNotValidException("");
        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
            throw new NoSuchPlayerException("");
        }
        return player;
    }

    @GetMapping("/players/count")
    public Integer getPlayerCount(ServletRequest request) {
//        Map<String, String[]> paramMap = request.getParameterMap();
//        List<Player> playerList;
        Pageable pageable = PageRequest.of(0, 3);
        Page<Player> playerList = playerService.findByCriteria(request, pageable);
//        return playerList.size();
        return Math.toIntExact(playerList.getTotalElements());
    }

    @PostMapping("/players/{id}")
    public Player updatePlayer(@PathVariable long id, @RequestBody Player player) {
        if (id < 1) {
            throw new IdIsNotValidException("");
        }
        Player newPlayer = playerService.updUpdatePlayer(id ,player);
//        if (newPlayer == null) {
//            throw new NoSuchPlayerException("");
//        }
        return newPlayer;
    }

    @PostMapping("/players/")
    public Player createPlayer(@RequestBody Player player) {
        Player newPlayer = playerService.updCreatePlayer(player);
//        if (player == null) {
//            throw new NoSuchPlayerException("");
//        }
        return newPlayer;
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable long id) {
        if (id < 1) {
            throw new IdIsNotValidException("");
        }
        Player player = playerService.getPlayer(id);
        if (player == null) {
            throw new NoSuchPlayerException("");
        }
        playerService.deletePlayerById(id);
    }
}
