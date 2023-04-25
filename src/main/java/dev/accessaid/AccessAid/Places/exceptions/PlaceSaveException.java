package dev.accessaid.AccessAid.Places.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceSaveException extends RuntimeException {

    private String message;

    public PlaceSaveException(String message) {
        super(message);
        this.message = message;
    }
}