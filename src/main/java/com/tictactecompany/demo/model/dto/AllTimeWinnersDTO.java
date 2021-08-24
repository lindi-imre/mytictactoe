package com.tictactecompany.demo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllTimeWinnersDTO {

    private int xWinnersCounter;
    private int oWinnersCounter;

}
