package com.tictactecompany.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Winning {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String player;
    private LocalDateTime timestamp;
}
