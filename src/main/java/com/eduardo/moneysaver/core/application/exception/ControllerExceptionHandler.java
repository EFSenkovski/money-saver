package com.eduardo.moneysaver.core.application.exception;

import com.eduardo.moneysaver.core.application.exception.error.SaldoInsuficienteException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.http.HttpResponse;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        var fields = exception.getFieldErrors().stream()
                .map(error -> String.format("Campo %s - %s", error.getField(), error.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ErrorMessage("Existem campos com valores inválidos", fields));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> EntityNotFoundExceptionHandler(EntityNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ErrorResp(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> IllegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ErrorResp(exception.getMessage()));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> RuntimeExceptionHandler(SaldoInsuficienteException exception) {
        return ResponseEntity.badRequest().body(new ErrorResp(exception.getMessage()));
    }
}
record ErrorMessage(String error, List<String> details) {
}
