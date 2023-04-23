package dev.accessaid.AccessAid.Comments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.exceptions.CommentException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;
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
        if (commentToDelete == null) {
            throw new CommentException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        User user = commentToDelete.getUser();
        Place place = commentToDelete.getPlace();
        if (user != null) {
            user.getComments().remove(commentToDelete);
        }
        if (place != null) {
            place.getComments().remove(commentToDelete);
        }
        try {
            commentRepository.deleteCommentById(id);
        } catch (Exception e) {
            System.out.println(e);
            throw new CommentException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete comment");
        }
        return commentToDelete;
    }

    public List<Comment> getAllCommentsByPlace(Place place) {
        return commentRepository.findAllCommentsByPlace(place);
    }

    public List<Comment> getAllCommentsByUser(User user) {
        return commentRepository.findAllCommentsByUser(user);
    }

}
