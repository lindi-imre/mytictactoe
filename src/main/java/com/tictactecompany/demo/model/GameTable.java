package com.tictactecompany.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tictactecompany.demo.model.dto.AllTimeWinnersDTO;
import com.tictactecompany.demo.model.enums.GameStatus;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Data
public class GameTable {

    private static GameTable gameTable;
    private Character[] fields = new Character[9];
    private GameStatus gameStatus = GameStatus.PLAYING;
    private static int xPlayerWinCounter;
    private static int oPlayerWinCounter;

    @JsonIgnore
    private boolean isSingletonGameTableResetted = true;

    // I create this static field to be able to easily check the winner
    private static int[][] winningLines = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    // The basic app will have only one gametable, I make a singleton for it
    public static GameTable getSingletonGameTable() {
        if(gameTable != null) {
            return gameTable;
        }
        gameTable = new GameTable();
        return gameTable;
    }

    public static GameTable resetSingletonGameTable() {
        getSingletonGameTable().resetGameTable();
        return getSingletonGameTable();
    }

    public void resetGameTable() {
        isSingletonGameTableResetted = true;
        fields = new Character[9];
        gameStatus = GameStatus.PLAYING;
    }

    public Character[] doMove(Move move) throws FieldIsNotEmptyException {
        if(fields[move.getPlace()] != null) {
            log.error("The following player try to overwrite a field. Player: {}, Field: {}", move.getPlayerSign(), move.getPlace());
            throw new FieldIsNotEmptyException("Field is not empty, choose a different one!");
        }
        fields[move.getPlace()] = move.getPlayerSign();
        if(!Arrays.stream(fields).anyMatch(field -> field == null)) {
            gameStatus = GameStatus.DUE;
        }
        log.info("The following movement happened. Player: {}, field: {}", move.getPlayerSign(), move.getPlace());
        calculateWinner();
        return fields;
    }

    public Character calculateWinner() {
        for (int i = 0; i < winningLines.length; i++) {
            if(fields[winningLines[i][0]] != null &&
                fields[winningLines[i][0]].equals(fields[winningLines[i][1]]) &&
                fields[winningLines[i][0]].equals(fields[winningLines[i][2]]))
            {
                this.gameStatus = GameStatus.FINISH;
                if(this.isSingletonGameTableResetted) {
                    if (fields[winningLines[i][0]].equals('X')) {
                        xPlayerWinCounter++;
                    } else {
                        oPlayerWinCounter++;
                    }
                    this.isSingletonGameTableResetted = false;
                }
                return fields[winningLines[i][0]];
            }
        }
        return null;
    }

    public static AllTimeWinnersDTO getAllTimeWinners() {
        return AllTimeWinnersDTO.builder()
                .oWinnersCounter(oPlayerWinCounter)
                .xWinnersCounter(xPlayerWinCounter)
                .build();
    }
}
