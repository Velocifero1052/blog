package ru.bagration.spring.exception.definition;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private final HttpStatus status;

    public BadRequestException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public BadRequestException(String message, Throwable cause, HttpStatus status){
        super(message, cause);
        this.status = status;
    }

}
