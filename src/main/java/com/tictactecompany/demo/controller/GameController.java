package com.tictactecompany.demo.controller;

import com.tictactecompany.demo.model.RestResponse;
import com.tictactecompany.demo.model.dto.AllTimeWinnersDTO;
import com.tictactecompany.demo.model.dto.CommandDTO;
import com.tictactecompany.demo.model.GameTable;
import com.tictactecompany.demo.model.Move;
import com.tictactecompany.demo.model.exception.FieldIsNotEmptyException;
import com.tictactecompany.demo.model.exception.UnknownCommandException;
import com.tictactecompany.demo.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/get-table")
    public GameTable getGameTable() {
        return GameTable.getSingletonGameTable();
    }

    @PostMapping("/move")
    public GameTable move(@RequestBody Move move) throws FieldIsNotEmptyException {
        gameService.makeMove(move);
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

    @GetMapping("/all-time-winners")
    public AllTimeWinnersDTO getAllTimeWinners() {
        return gameService.getAllTimeWinners();
    }

    @ExceptionHandler(FieldIsNotEmptyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestResponse handleInvalidMoveException(FieldIsNotEmptyException e) {
        return RestResponse.buildFailure(e.getMessage());
    }

    @ExceptionHandler(UnknownCommandException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnknownCommandException(UnknownCommandException e) {
        return e.getMessage();
    }
}
