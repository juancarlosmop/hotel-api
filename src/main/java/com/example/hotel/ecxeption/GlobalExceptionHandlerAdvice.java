package com.example.hotel.ecxeption;

import com.example.hotel.constants.StatusCodeEnum;
import com.example.hotel.dto.ErrorResponse;
import com.example.hotel.dto.FieldValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(LoginException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.UNAUTHORIZED.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(UserNotFoundException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(ReservationNotFoundException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(RoomNotFoundException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(RoomAlreadyExistsException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.CONFLICT.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex){
        ErrorResponse resp = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .message(ex.getMessage()).build();
        return new ResponseEntity<>(resp,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldValidation> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        FieldValidation validation=FieldValidation.builder()
                .code("ERROR")
                .message("FIELD ERROR")
                .errors(errors)
                .build();
        return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
    }
}
