package dev.accessaid.AccessAid.Comments.service;

import java.util.List;

import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentSaveException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;

public interface CommentService {

    List<Comment> getComments();

    Comment getCommentById(Integer id) throws CommentNotFoundException;

    Comment createComment(Comment comment) throws CommentSaveException;

    Comment changeComment(Comment comment) throws CommentNotFoundException;

    Comment removeComment(Integer id) throws CommentNotFoundException;

    List<Comment> getCommentsByPlace(Place place) throws CommentNotFoundException;

    List<Comment> getCommentsByUser(User user) throws CommentNotFoundException;
};
