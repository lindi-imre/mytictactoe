package com.tictactecompany.demo.model;

import lombok.Data;

@Data
public class RestResponse {
    private Boolean success;
    private String message;

    private RestResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static RestResponse buildSuccess(String message) {
        return new RestResponse(true, message);
    }

    public static RestResponse buildFailure(String message) {
        return new RestResponse(false, message);
    }
}
