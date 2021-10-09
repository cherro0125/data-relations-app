package com.kaliszewski.datarelations.data.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DataMessageResponse<T> extends MessageResponse {
    T data;

    private DataMessageResponse(String message, HttpStatus status, T data) {
        super(message,status);
        this.data = data;
    }

    public static <T> DataMessageResponse<T> createSimpleOk(T data) {
        return new DataMessageResponse<>("OK", HttpStatus.OK, data);
    }

    public static <T> DataMessageResponse<T> createSimpleOk(T data, HttpStatus status) {
        return new DataMessageResponse<>("OK", status, data);
    }

    public static <T> DataMessageResponse<T> createSimpleOk(T data, HttpStatus status, String additionalMessage) {
        return new DataMessageResponse<>(additionalMessage, status, data);
    }

    public ResponseEntity<DataMessageResponse<T>> toResponseEntity() {
        return new ResponseEntity<>(this, this.getStatus());
    }
}
