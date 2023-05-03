package dev.accessaid.AccessAid.Ratings.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingNotFoundException extends RuntimeException {

    private String message;

    public RatingNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
