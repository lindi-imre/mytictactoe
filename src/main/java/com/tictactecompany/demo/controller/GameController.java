package com.tictactecompany.demo.controller;

import com.tictactecompany.demo.model.GameTable;
import com.tictactecompany.demo.model.Move;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/game")
public class GameController {

    @GetMapping("/get-table")
    public GameTable getGameTable() {
        return GameTable.getSingletonGameTable();
    }

    @PostMapping("/move")
    public GameTable move(@RequestBody Move move) throws FieldIsNotEmptyException {
        GameTable.getSingletonGameTable().doMove(move);
        return GameTable.getSingletonGameTable();
    }

    @ExceptionHandler(FieldIsNotEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidMoveException(FieldIsNotEmptyException e) {
        return e.getMessage();
    }
}
