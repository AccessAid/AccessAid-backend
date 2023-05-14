package dev.accessaid.AccessAid.Contact.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ContactNotFoundException extends RuntimeException {

    private String message;

    public ContactNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
