package dev.accessaid.AccessAid.Comments.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.exceptions.CommentException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentSaveException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import jakarta.transaction.Transactional;



@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();

    }

    @Override
    public Comment getCommentById(Integer id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new CommentNotFoundException("Comment not found");
        }

    }

    @Override
    public Comment createComment(Comment comment) throws CommentSaveException {

        return commentRepository.save(comment);
    }

    @Override
    public Comment changeComment(Comment comment) throws CommentNotFoundException {
        Optional<Comment> commentToUpdate = commentRepository.findById(comment.getId());
        if (!commentToUpdate.isPresent()) {
            throw new CommentNotFoundException("Comment not found");

        }
        Comment updatedComment = commentToUpdate.get();
        return commentRepository.save(updatedComment);

    }

    @Override
    @Transactional
    public Comment removeComment(Integer id) throws CommentNotFoundException {
        Optional<Comment> commentToDelete = commentRepository.findById(id);
        if (!commentToDelete.isPresent()) {
            throw new CommentNotFoundException("Comment not found");
        }
        Comment deletedComment = commentToDelete.get();
        User user = deletedComment.getUser();
        Place place = deletedComment.getPlace();
        if (user != null) {
            user.getComments().remove(deletedComment);
        }
        if (place != null) {
            place.getComments().remove(deletedComment);
        }
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
            throw new CommentException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete comment");
        }
        return commentToDelete.get();
    }

    @Override
    public List<Comment> getCommentsByPlace(Place place) {
        return commentRepository.findAllCommentsByPlace(place);
    }

    @Override
    public List<Comment> getCommentsByUser(User user) {
        return commentRepository.findAllCommentsByUser(user);
    }

}
