package dev.accessaid.AccessAid.Contact.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactSaveException extends RuntimeException {

    private String message;

    public ContactSaveException(String message) {
        super(message);
        this.message = message;
    }
}
