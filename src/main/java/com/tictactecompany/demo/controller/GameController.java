package com.tictactecompany.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/game")
public class GameController {

    @GetMapping("/get-table")
    public String getGameTable() {
        return "It works!";
    }
}
