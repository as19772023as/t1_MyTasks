package ru.strebkov.t1_MyTasks.exception;


import jakarta.validation.ConstraintDeclarationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handlerServerError(InternalServerErrorException e) {
        return new ResponseEntity<>("Exception: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchTasksEndpointException.class)
    public ResponseEntity<String> handlerNoSuchError(NoSuchTasksEndpointException e) {
        return new ResponseEntity<>("Exception: " + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintDeclarationException.class)
    public ResponseEntity<String> onConstraintValidationException(ConstraintDeclarationException e) {
        return new ResponseEntity<>("Exception: Даные введены не корректно, провертье все поля и валидность даты ",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("Exception: Даные введены не корректно, провертье все поля и валидность даты ",
                HttpStatus.BAD_REQUEST);
    }

}