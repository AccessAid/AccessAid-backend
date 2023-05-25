package dev.accessaid.AccessAid.Comments.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentSaveException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;

public interface CommentService {

    Page<Comment> getComments(Pageable pageable) throws CommentNotFoundException;

    Comment getCommentById(Integer id) throws CommentNotFoundException;

    Comment createComment(Comment comment) throws CommentSaveException;

    Comment changeComment(Integer id, Comment comment) throws CommentNotFoundException;

    Comment removeComment(Integer id) throws CommentNotFoundException;

    Page<Comment> getCommentsByPlace(Place place, Pageable pageable) throws CommentNotFoundException;

    Page<Comment> getCommentsByUser(User user, Pageable pageable) throws CommentNotFoundException;
};
