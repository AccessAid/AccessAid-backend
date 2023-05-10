package dev.accessaid.AccessAid.Comments.utils;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;

public class CommentUtils {

    public static Comment getLastRepliedComment(Comment comment) {
        Comment lastRepliedComment = comment;
        while (lastRepliedComment.getRepliedComment() != null) {
            lastRepliedComment = lastRepliedComment.getRepliedComment();
        }
        return lastRepliedComment;
    }

    public static void setRepliedCommentAndReplyToComment(Comment repliedComment, Comment savedComment,
            CommentRepository commentRepository) {
        if (repliedComment.isHasResponse()) {
            Comment lastRepliedComment = CommentUtils.getLastRepliedComment(repliedComment);
            lastRepliedComment.setRepliedComment(savedComment);
            lastRepliedComment.setHasResponse(true);
            commentRepository.save(lastRepliedComment);

            savedComment.setReplyToComment(lastRepliedComment);
            commentRepository.save(savedComment);
        } else {
            repliedComment.setRepliedComment(savedComment);
            repliedComment.setHasResponse(true);
            commentRepository.save(repliedComment);

            savedComment.setReplyToComment(repliedComment);
            commentRepository.save(savedComment);
        }
    }

}
