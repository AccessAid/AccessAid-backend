package dev.accessaid.AccessAid.Comments.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveException extends RuntimeException {

    private String message;

    public CommentSaveException(String message) {
        super(message);
        this.message = message;
    }
}