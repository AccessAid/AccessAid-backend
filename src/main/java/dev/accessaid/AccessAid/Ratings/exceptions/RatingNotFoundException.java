package dev.accessaid.AccessAid.Ratings.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RatingNotFoundException extends RuntimeException {

    private String message;

    public RatingNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
