package dev.accessaid.AccessAid.Comments.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentNotFoundException extends RuntimeException {

    private String message;

    public CommentNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}