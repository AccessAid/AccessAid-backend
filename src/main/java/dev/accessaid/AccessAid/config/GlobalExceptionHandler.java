package dev.accessaid.AccessAid.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error occurred.")
    public ErrorResponse handleException(Exception e) {
        return new ErrorResponse("Internal server error: " + e.getMessage());
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

    @ExceptionHandler(RatingNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Rating not found.")
    @ResponseBody
    public ErrorResponse handleRatingNotFoundException(RatingNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }
}