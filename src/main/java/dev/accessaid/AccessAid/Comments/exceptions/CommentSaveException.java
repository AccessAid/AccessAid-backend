package dev.accessaid.AccessAid.Comments.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentSaveException extends RuntimeException {

    private String message;

    public CommentSaveException(String message) {
        super(message);
        this.message = message;
    }
}