package com.hackacode.themepark.controller;

import com.hackacode.themepark.exception.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    // Controla excepciones de Spring Validation

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Map<Path, String>> constraintViolationException(ConstraintViolationException ex) {

        Map<Path, String> errorDetails = new HashMap<>();

        Path campo = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getPropertyPath).get();

        String mj =  ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(ConstraintViolation::getMessage).get();

        errorDetails.put(campo, mj);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    // Controla excepciones de datos no encontrados

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({IdNotFoundException.class, RoleNotFoundException.class,
            DniNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDetails> notFoundExceptions(Exception ex) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value() + " NOT_FOUND");
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    // Controla Bad Requests y excepciones generales

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DniExistsException.class, EmailExistsException.class, HoursExistsException.class,
                        NameExistsException.class, RoleExistsException.class, Exception.class})
    public ResponseEntity<ErrorDetails> badRequestExceptions(Exception ex) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    // Controla errores de tipeo en la URL

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> typingErrorExceptions() {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value() + " NOT_FOUND");
        errorDetails.setMessage("Error de tipeo: Revise los datos ingresados.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

}
