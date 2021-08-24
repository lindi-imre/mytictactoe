package com.tictactecompany.demo.service;

import com.tictactecompany.demo.model.GameTable;
import com.tictactecompany.demo.model.Move;
import com.tictactecompany.demo.model.Winning;
import com.tictactecompany.demo.model.dto.AllTimeWinnersDTO;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

    private final EntityManager entityManager;

    public GameTable makeMove(Move move) throws FieldIsNotEmptyException {
        GameTable.getSingletonGameTable().doMove(move);
        Character winner = GameTable.getSingletonGameTable().calculateWinner();
        if(winner != null) {
            saveWinning(winner);
        }
        return GameTable.getSingletonGameTable();
    }

    public void saveWinning(Character player) {
        Winning winning = Winning.builder()
                .player(String.valueOf(player))
                .timestamp(LocalDateTime.now())
                .build();
        entityManager.persist(winning);
    }

    public AllTimeWinnersDTO getAllTimeWinners() {
        Long xCounter = winningOrDrawCounterByPlayer("X");
        Long oCounter = winningOrDrawCounterByPlayer("O");
        Long drawsCounter = winningOrDrawCounterByPlayer("D");

        // Count query returns with long type, primitive conversion needed
        return AllTimeWinnersDTO.builder()
                .xWinnersCounter(xCounter.intValue())
                .oWinnersCounter(oCounter.intValue())
                .drawsCounter(drawsCounter.intValue())
                .build();
    }

    private Long winningOrDrawCounterByPlayer(String player) {
        return entityManager.createQuery("select count(w) from Winning w " +
                "where w.player = ?1", Long.class)
                .setParameter(1, player)
                .getResultStream()
                .findFirst()
                .orElse(0L);
    }
}
