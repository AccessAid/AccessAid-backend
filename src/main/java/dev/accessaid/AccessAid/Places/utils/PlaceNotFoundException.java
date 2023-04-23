package dev.accessaid.AccessAid.Places.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceNotFoundException extends RuntimeException {

    private String message;

    public PlaceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}