package com.example.demo.exception.adviser;


import com.example.demo.exception.SenderException;
import com.example.demo.exception.UserException;
import com.example.demo.util.Response;
import com.example.demo.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler({UserException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleUserException(UserException exception) {
        log.error("[USER]" + exception.getMessage());
        Response response = ResponseBuilder.composeFailureResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({SenderException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleSenderException(SenderException exception) {
        log.error("[SENDER]" + exception.getMessage());
        Response response = ResponseBuilder.composeFailureResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
