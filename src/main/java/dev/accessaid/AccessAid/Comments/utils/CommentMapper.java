package dev.accessaid.AccessAid.Comments.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.response.CommentResponse;

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

    public static Page<CommentResponse> toCommentResponses(Page<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentMapper::toCommentResponse)
                .collect(Collectors.toList());
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }

}
