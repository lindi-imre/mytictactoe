package com.tictactecompany.demo.controller;

import com.tictactecompany.demo.model.CommandDTO;
import com.tictactecompany.demo.model.GameTable;
import com.tictactecompany.demo.model.Move;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import com.tictactecompany.demo.model.exception.UnknownCommandException;
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

    @PostMapping("/reset")
    public GameTable reset(@RequestBody CommandDTO commandDTO) throws UnknownCommandException {
        if(commandDTO.getCommand() != null && commandDTO.getCommand().equals("reset")) {
            return GameTable.resetSingletonGameTable();
        }
        throw new UnknownCommandException("Unknown command");
    }

    @GetMapping("/winner")
    public Character getWinner() {
        return GameTable.getSingletonGameTable().calculateWinner();
    }

    @ExceptionHandler(FieldIsNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleInvalidMoveException(FieldIsNotEmptyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnknownCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnknownCommandException(UnknownCommandException e) {
        return e.getMessage();
    }
}
