package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exception_handling.IdIsNotValidException;
import com.game.exception_handling.NoSuchPlayerException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import java.util.*;
import java.util.logging.Level;

//@Component
@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Page<Player> findByCriteria(ServletRequest request, Pageable pageable){
        String playerName = null;
        String title = null;
        Race race = null;
        Profession profession = null;
        Long after = null;
        Long before = null;
        Boolean banned = null;
        Integer minExperience = null;
        Integer maxExperience = null;
        Integer minLevel = null;
        Integer maxLevel = null;
        Date beforeDate = null;
        Date afterDate = null;
        Map<String, String[]> paramMap = request.getParameterMap();
        if (paramMap.containsKey("name")) {
            String[] params = paramMap.get("name");
            playerName = params[0];
        }
        if (paramMap.containsKey("title")) {
            String[] params = paramMap.get("title");
            title = params[0];
        }
        if (paramMap.containsKey("race")) {
            String[] params = paramMap.get("race");
            race = Race.valueOf(params[0]);
        }
        if (paramMap.containsKey("profession")) {
            String[] params = paramMap.get("profession");
            profession = Profession.valueOf(params[0]);
        }
        if (paramMap.containsKey("after")) {
            String[] params = paramMap.get("after");
            after = Long.valueOf(params[0]);
        }
        if (paramMap.containsKey("before")) {
            String[] params = paramMap.get("before");
            before = Long.valueOf(params[0]);
        }
        if (before != null) {
            beforeDate = new Date(before);
        }
        if (after != null) {
            afterDate = new Date(after);
        }
        if (paramMap.containsKey("banned")) {
            String[] params = paramMap.get("banned");
            banned = Boolean.parseBoolean(params[0]);
        }
        if (paramMap.containsKey("minExperience")) {
            String[] params = paramMap.get("minExperience");
            minExperience = Integer.valueOf(params[0]);
        }
        if (paramMap.containsKey("maxExperience")) {
            String[] params = paramMap.get("maxExperience");
            maxExperience = Integer.valueOf(params[0]);
        }
        if (paramMap.containsKey("minLevel")) {
            String[] params = paramMap.get("minLevel");
            minLevel = Integer.valueOf(params[0]);
        }
        if (paramMap.containsKey("maxLevel")) {
            String[] params = paramMap.get("maxLevel");
            maxLevel = Integer.valueOf(params[0]);
        }

        String finalPlayerName = playerName;
        String finalTitle = title;
        Race finalRace = race;
        Profession finalProfession = profession;
        Integer finalMinExperience = minExperience;
        Integer finalMaxExperience = maxExperience;
        Integer finalMinLevel = minLevel;
        Integer finalMaxLevel = maxLevel;
        Boolean finalBanned = banned;
        Date finalBeforeDate = beforeDate;
        Date finalAfterDate = afterDate;
        Page<Player> pages = playerRepository.findAll(new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(finalPlayerName !=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("name"), "%"+ finalPlayerName +"%")));
                }
                if(finalTitle !=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%"+ finalTitle +"%")));
                }
                if(finalRace !=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("race"), finalRace)));
                }
                if(finalProfession !=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("profession"), finalProfession)));
                }
                // ****
                if( finalAfterDate !=null && finalBeforeDate !=null){
                    predicates.add(criteriaBuilder.between(root.get("birthday"), finalAfterDate, finalBeforeDate));
                }
                if( finalAfterDate !=null && finalBeforeDate ==null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), finalAfterDate));
                }
                if( finalAfterDate ==null && finalBeforeDate !=null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), finalBeforeDate));
                }
                // ****
                if(Boolean.TRUE.equals(finalBanned)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("banned"), 1)));
                }
                else if (Boolean.FALSE.equals(finalBanned)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("banned"), 0)));
                }
                // ****
                if(finalMinExperience !=null && finalMaxExperience !=null){
                    predicates.add(criteriaBuilder.between(root.get("experience"), finalMinExperience, finalMaxExperience));
                }
                if(finalMinExperience !=null && finalMaxExperience ==null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), finalMinExperience));
                }
                if(finalMinExperience ==null && finalMaxExperience !=null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), finalMaxExperience));
                }
                // ****
                if(finalMinLevel !=null && finalMaxLevel !=null){
                    predicates.add(criteriaBuilder.between(root.get("level"), finalMinLevel, finalMaxLevel));
                }
                if(finalMinLevel !=null && finalMaxLevel ==null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), finalMinLevel));
                }
                if(finalMinLevel ==null && finalMaxLevel !=null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), finalMaxLevel));
                }
                // ****
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        return pages;
//        List<Player> playerList1 = pages.getContent();
//        playerList1.add(0, );
//        return pages.getContent();
    }


    @Override
    public Player updCreatePlayer(Player player) {
        if (player.getName() == null || player.getTitle() == null || player.getRace() == null
            || player.getProfession() == null || player.getBirthday() == null
            || player.getExperience() == null) {
            throw new IdIsNotValidException("");
        }
        if (player.getName().length() > 12 || player.getTitle().length() > 30 || player.getName().isEmpty()) {
            throw new IdIsNotValidException("");
        }
        if (player.getExperience() > 10000000 || player.getExperience() < 0) {
            throw new IdIsNotValidException("");
        }
        if (player.getBirthday().getTime() < 0 || player.getBirthday().getTime() > System.currentTimeMillis()) {
            throw new IdIsNotValidException("");
        }
        Player newPlayer = player;
        if (player.isBanned() == null) {
            newPlayer.setBanned(false);
        }
        Integer level = (int) ((Math.sqrt(2500 + (200*player.getExperience())) - 50) / 100);
        newPlayer.setLevel(level);
        Integer untilNextLevel = (50 * (level + 1) * (level + 2)) - player.getExperience();
        newPlayer.setUntilNextLevel(untilNextLevel);

        playerRepository.save(newPlayer);


        return newPlayer;
    }

    @Override
    public Player updUpdatePlayer(long id, Player player) {
        Player newPlayer = getPlayer(id);
        if (newPlayer == null) {
            throw new NoSuchPlayerException("");
        }
        if (player.getName() != null) {
            newPlayer.setName(player.getName());
        }
        if (player.getTitle() != null) {
            newPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            newPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            newPlayer.setProfession(player.getProfession());
        }
//        Long inDBPlayerDate = newPlayer.getBirthday().getTime();
        if (player.getBirthday() != null) {
            if (player.getBirthday().getTime() < 0) {
                throw new IdIsNotValidException("");
            }
            newPlayer.setBirthday(player.getBirthday());
        }
        if (player.isBanned() != null) {
            newPlayer.setBanned(player.isBanned());
        }
        if (player.getExperience() != null) {
            if ( (player.getExperience() > 10000000) || (player.getExperience() < 0) ){
                throw new IdIsNotValidException("");
            }
            newPlayer.setExperience(player.getExperience());
            Integer level = (int) ((Math.sqrt(2500 + (200*player.getExperience())) - 50) / 100);
            newPlayer.setLevel(level);
            Integer untilNextLevel = (50 * (level + 1) * (level + 2)) - player.getExperience();
            newPlayer.setUntilNextLevel(untilNextLevel);
        }

        playerRepository.save(newPlayer);

        return newPlayer;
    }

    @Override
    public Player getPlayer(long id) {
//        Player player = null;
//        Optional<Player> optional = playerRepository.findById(id);
//        if (optional.isPresent()) {
//            player = optional.get();
//        }
        return playerRepository.findById(id);
    }

    @Override
    @Transactional
    public void deletePlayerById(long id) {
        playerRepository.deletePlayerById(id);
    }

}
