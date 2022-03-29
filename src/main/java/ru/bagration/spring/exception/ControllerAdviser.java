package ru.bagration.spring.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bagration.spring.exception.definition.BadRequestException;

import static ru.bagration.spring.utils.ResponseData.responseBadRequest;

@ControllerAdvice
public class ControllerAdviser extends ResponseEntityExceptionHandler{

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request){
        var responseData = responseBadRequest(exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(responseData);
    }

}
