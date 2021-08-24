package com.tictactecompany.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllTimeWinnersDTO {

    @JsonProperty("xWinnersCounter")
    private int xWinnersCounter;

    @JsonProperty("oWinnersCounter")
    private int oWinnersCounter;

    @JsonProperty("drawsCounter")
    private int drawsCounter;

}
