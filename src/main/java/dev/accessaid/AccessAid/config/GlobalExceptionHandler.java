package dev.accessaid.AccessAid.config;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentSaveException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error occurred.")
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("Internal server error: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error occurred.")
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("Internal server error: " + e.getMessage());
    }

    @ExceptionHandler(PlaceSaveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Place save failed.")
    public ErrorResponse handlePlaceSaveException(PlaceSaveException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(PlaceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Place not found.")
    @ResponseBody
    public ErrorResponse handlePlaceNotFoundException(PlaceNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found.")
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comment not found.")
    @ResponseBody
    public ErrorResponse handleCommentNotFoundException(CommentNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(CommentSaveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Comment save failed.")
    public ErrorResponse handleCommentSaveException(CommentSaveException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Rating not found.")
    @ResponseBody
    public ErrorResponse handleRatingNotFoundException(RatingNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(RatingSaveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Rating save failed.")
    public ErrorResponse handleRatingSaveException(RatingSaveException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Profile not found.")
    @ResponseBody
    public ErrorResponse handleProfileNotFoundException(ProfileNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ProfileSaveException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Profile save failed.")
    public ErrorResponse handleProfileSaveException(ProfileSaveException e) {
        return new ErrorResponse(e.getMessage());
    }
}
