package dev.accessaid.AccessAid.Comments.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.exceptions.CommentSaveException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import dev.accessaid.AccessAid.Comments.utils.CommentUtils;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<Comment> getComments() throws CommentNotFoundException {
        return commentRepository.findAll();

    }
    @Override
    public Page<Comment> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment getCommentById(Integer id) throws CommentNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));

    }

    @Override
    public Comment createComment(Comment comment) throws
            UserNotFoundException, PlaceNotFoundException, CommentNotFoundException, CommentSaveException {

        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + comment.getUser().getId()));

        Place place = placeRepository.findById(comment.getPlace().getId())
                .orElseThrow(
                        () -> new PlaceNotFoundException("Place not found with id: " + comment.getPlace().getId()));

        if (comment.getReplyToComment() != null) {
            Comment replyToComment = comment.getReplyToComment();
            if (replyToComment.getId() != null) {
                Comment repliedComment = commentRepository.findById(replyToComment.getId())
                        .orElseThrow(() -> new CommentNotFoundException("Reply-to comment not found"));
            } else {
                throw new CommentSaveException("reply-to comment can not be null");
            }
        }

        Comment savedComment = commentRepository.save(comment);

        if (comment.getReplyToComment() != null) {
            Comment replyToComment = comment.getReplyToComment();
            if (replyToComment.getId() != null) {
                Comment repliedComment = commentRepository.findById(replyToComment.getId()).get();

                CommentUtils.setRepliedCommentAndReplyToComment(repliedComment, savedComment, commentRepository);
            }
        }

        place.addComment(savedComment);

        if (!place.getUsers().contains(user)) {
            place.getUsers().add(user);
            placeRepository.save(place);
        }
        return savedComment;
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
        Comment commentToDelete = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
        commentRepository.deleteById(id);
        return commentToDelete;
    }

    @Override
    public List<Comment> getCommentsByPlace(Place place) throws PlaceNotFoundException {
        return commentRepository.findAllCommentsByPlace(place);
    }

    @Override
    public Page<Comment> getCommentsByPlace(Place place, Pageable pageable) throws PlaceNotFoundException {
        return commentRepository.findAllCommentsByPlace(place, pageable);
    }

    @Override
    public List<Comment> getCommentsByUser(User user) throws UserNotFoundException {
        return commentRepository.findAllCommentsByUser(user);
    }

    @Override
    public Page<Comment> getCommentsByUser(User user, Pageable pageable) throws UserNotFoundException {
        return commentRepository.findAllCommentsByUser(user, pageable);
    }

}
