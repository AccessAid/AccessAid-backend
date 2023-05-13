package dev.accessaid.AccessAid.Contact.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactNotFoundException extends RuntimeException {

    private String message;

    public ContactNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
