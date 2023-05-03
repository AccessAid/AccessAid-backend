package dev.accessaid.AccessAid.Ratings.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingSaveException extends RuntimeException {

    private String message;

    public RatingSaveException(String message) {
        super(message);
        this.message = message;
    }
}