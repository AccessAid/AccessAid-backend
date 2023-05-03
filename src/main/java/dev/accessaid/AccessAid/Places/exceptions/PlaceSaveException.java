package dev.accessaid.AccessAid.Places.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlaceSaveException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Error saving place.";

    String message = DEFAULT_MESSAGE;

    public PlaceSaveException(String message) {
        super(message);
    }
}