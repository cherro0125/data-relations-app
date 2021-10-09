package com.kaliszewski.datarelations.data.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class MessageResponse {
    private String message;
    private HttpStatus status;
    private Integer statusCode;

    public MessageResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }
}
