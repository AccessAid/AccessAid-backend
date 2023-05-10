package dev.accessaid.AccessAid.Comments.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.response.CommentResponse;

public class CommentMapper {

    public static CommentResponse toCommentResponse(Comment comment) {
        Integer replyToComment = null;
        Integer responseByComment = null;
        boolean hasResponse = false;
        if (comment.getReplyToComment() != null) {
            replyToComment = comment.getReplyToComment().getId();
        }
        if (comment.getResponseByComment() != null) {
            responseByComment = comment.getResponseByComment().getId();
            hasResponse = true;
        }

        return new CommentResponse(
                comment.getId(),
                comment.getComment(),
                comment.getUser().getId(),
                comment.getPlace().getId(),
                replyToComment,
                responseByComment,
                hasResponse);
    }

    public static List<CommentResponse> toCommentResponses(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }

    public static Page<CommentResponse> toCommentResponses(Page<Comment> comments, Pageable pageable) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentMapper::toCommentResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(commentResponses, pageable, comments.getTotalElements());
    }

}
