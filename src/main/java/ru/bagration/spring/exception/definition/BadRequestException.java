package ru.bagration.spring.exception.definition;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    private final Integer status;

    public BadRequestException(String message, Integer status){
        super(message);
        this.status = status;
    }

    public BadRequestException(String message, Throwable cause, Integer status){
        super(message, cause);
        this.status = status;
    }

}
