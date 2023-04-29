package dev.accessaid.AccessAid.Profile.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileSaveException extends RuntimeException {

    private String message;

    public ProfileSaveException(String message) {
        super(message);
        this.message = message;
    }
}
