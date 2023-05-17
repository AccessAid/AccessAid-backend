package dev.accessaid.AccessAid.Contact.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailSendException extends RuntimeException {

    private String message;

    public EmailSendException(String message) {
        super(message);
        this.message = message;
    }
}
