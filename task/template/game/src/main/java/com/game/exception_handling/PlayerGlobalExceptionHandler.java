package com.game.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PlayerGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handleException(
            NoSuchPlayerException exception
    )  {
        PlayerIncorrectData incorrectData = new PlayerIncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<PlayerIncorrectData> handleException(
            IdIsNotValidException exception
    )  {
        PlayerIncorrectData incorrectData = new PlayerIncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler
//    public ResponseEntity<PlayerIncorrectData> handleException(
//            Exception exception
//    )  {
//        PlayerIncorrectData incorrectData = new PlayerIncorrectData();
//        incorrectData.setInfo(exception.getMessage());
//
//        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
//    }
}
