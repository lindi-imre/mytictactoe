package com.tictactecompany.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tictactecompany.demo.model.dto.AllTimeWinnersDTO;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import lombok.Data;

@Data
public class GameTable {

    private static GameTable gameTable;
    private Character[] fields = new Character[9];
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
        fields = new Character[9];
    }

    public Character[] doMove(Move move) throws FieldIsNotEmptyException {
        if(fields[move.getPlace()] != null) {
            throw new FieldIsNotEmptyException("Field is not empty, choose a different one!");
        }
        fields[move.getPlace()] = move.getPlayerSign();
        return fields;
    }

    public Character calculateWinner() {
        for (int i = 0; i < fields.length; i++) {
            if(fields[winningLines[i][0]] != null &&
                fields[winningLines[i][0]].equals(fields[winningLines[i][1]]) &&
                fields[winningLines[i][0]].equals(fields[winningLines[i][2]]))
            {
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
