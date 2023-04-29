package dev.accessaid.AccessAid.Profile.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileNotFoundException extends RuntimeException {

    private String message;

    public ProfileNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
