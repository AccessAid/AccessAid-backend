package dev.accessaid.AccessAid.User.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveException extends RuntimeException {

    private String message;

    public UserSaveException(String message) {
        super(message);
        this.message = message;
    }
}
