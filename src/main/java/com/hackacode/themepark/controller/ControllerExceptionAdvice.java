package com.hackacode.themepark.controller;

import com.hackacode.themepark.exception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@Tag(name="Controlador de excepciones", description = "Controla excepciones de Spring Validation")
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @Operation(
            summary = "Devuelve excepciones de parametros",
            description = "Captura y devuelve excepciones de validez de parametros"
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validException(MethodArgumentNotValidException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorDetails.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @Operation(
            summary = "Devuelve excepciones de datos no encontrados",
            description = "Captura y devuelve codigo de estado 404 no encontrado, con excepciones de datos no encontrados"
    )
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler({IdNotFoundException.class, RoleNotFoundException.class,
            DniNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorDetails> notFoundExceptions(Exception ex) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value() + " NOT_FOUND");
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

    @Operation(
            summary = "Devuelve excepciones generales",
            description = "Captura y devuelve codigo de estado 400 con excepciones generales"
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DniExistsException.class, EmailExistsException.class, HoursExistsException.class,
                        NameExistsException.class, RoleExistsException.class, Exception.class})
    public ResponseEntity<ErrorDetails> badRequestExceptions(Exception ex) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        errorDetails.setMessage(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @Operation(
            summary = "Devuelve excepciones generales",
            description = "Captura y devuelve codigo de estado 400 con excepciones de formatos mal ingresados"
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDetails> badRequestFormatExceptions() {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
        errorDetails.setMessage("El tipo de formato ingresado es incorrecto");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @Operation(
            summary = "Devuelve excepciones generales",
            description = "Captura y devuelve codigo de estado 404 con errores de tipeo en la URL"
    )
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> typingErrorExceptions() {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value() + " NOT_FOUND");
        errorDetails.setMessage("Error de tipeo: Revise los datos ingresados.");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
    }

}
