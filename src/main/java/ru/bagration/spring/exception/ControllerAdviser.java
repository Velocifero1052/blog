package ru.bagration.spring.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.bagration.spring.exception.definition.BadRequestException;
import ru.bagration.spring.service.ErrorMessageService;
import ru.bagration.spring.utils.LangContext;
import ru.bagration.spring.utils.ResponseData;

import static ru.bagration.spring.utils.ResponseData.response;


@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdviser extends ResponseEntityExceptionHandler{

    private final ErrorMessageService errorMessageService;

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request){
        return response(exception.getMessage(), exception.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        var lang = LangContext.getLang();

        var responseData = new ResponseData<>(null,
                errorMessageService.findByKeyAndLang(errorMessage, lang));
        return ResponseEntity.badRequest().body(responseData);
    }



}
