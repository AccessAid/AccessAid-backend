package dev.accessaid.AccessAid.Comments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CommentException.class })
    public ResponseEntity<Object> handleCommentException(CommentException ex) {
        HttpStatus status = ex.getHttpStatus();
        String message = ex.getMessage();
        return ResponseEntity.status(status).body(message);
    }
}
