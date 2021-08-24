package com.tictactecompany.demo.controller;

import com.tictactecompany.demo.model.GameTable;
import com.tictactecompany.demo.model.Move;
import lombok.extern.slf4j.Slf4j;
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
    public GameTable move(@RequestBody Move move) {
        GameTable.getSingletonGameTable().doMove(move);
        return GameTable.getSingletonGameTable();
    }
}
