package dev.accessaid.AccessAid.Comments.utils;

import java.util.List;
import java.util.stream.Collectors;

import dev.accessaid.AccessAid.Comments.Response.CommentResponse;
import dev.accessaid.AccessAid.Comments.model.Comment;

public class CommentMapper {

    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getId(),
                comment.getPlace().getId());
    }

    public static List<CommentResponse> toCommentResponses(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }

}
