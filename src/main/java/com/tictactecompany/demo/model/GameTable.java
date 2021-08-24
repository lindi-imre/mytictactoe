package com.tictactecompany.demo.model;

import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import lombok.Data;

@Data
public class GameTable {

    private static GameTable gameTable;
    private Character[] fields = new Character[9];

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

    public Character[] doMove(Move move) throws FieldIsNotEmptyException {
        if(fields[move.getPlace()] != null) {
            throw new FieldIsNotEmptyException("Field is not empty, choose a different one!");
        }
        fields[move.getPlace()] = move.getPlayerSign();
        return fields;
    }
}
