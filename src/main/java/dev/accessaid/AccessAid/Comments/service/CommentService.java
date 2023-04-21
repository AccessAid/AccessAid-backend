package dev.accessaid.AccessAid.Comments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.exceptions.CommentException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments() {
        return commentRepository.findAllComments();

    }

    public Comment getCommentById(Integer id) {
        return commentRepository.findCommentById(id);

    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);

    }

    public Comment changeComment(Comment comment) {
        Comment commentToUpdate = commentRepository.findCommentById(comment.getId());
        if (commentToUpdate == null) {
            throw new CommentException(HttpStatus.NOT_FOUND, "Comment not found");

        }
        commentToUpdate.setComment(comment.getComment());
        return commentRepository.save(commentToUpdate);

    }

    @Transactional
    public Comment removeComment(Integer id) {
        Comment commentToDelete = commentRepository.findCommentById(id);
        System.out.println("COMMENT TO DELETE" + commentToDelete);
        if (commentToDelete == null) {
            throw new CommentException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepository.deleteCommentById(id);
        return commentToDelete;
    }

}
