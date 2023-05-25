package dev.accessaid.AccessAid.Comments.service;

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
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private EntityManager entityManager;

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
    public Comment createComment(Comment comment)
            throws UserNotFoundException, PlaceNotFoundException, CommentNotFoundException, CommentSaveException {

        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + comment.getUser().getId()));

        Place place = placeRepository.findById(comment.getPlace().getId())
                .orElseThrow(
                        () -> new PlaceNotFoundException("Place not found with id: " + comment.getPlace().getId()));

        if (comment.getReplyToComment() != null) {
            Comment replyToComment = comment.getReplyToComment();
            if (replyToComment.getId() != null) {
                commentRepository.findById(replyToComment.getId())
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

        if (commentToDelete.getReplyToComment() != null && commentToDelete.getRepliedComment() == null) {
            Query query = entityManager.createQuery("FROM Comment c WHERE c.repliedComment.id = :repliedCommentId");
            query.setParameter("repliedCommentId", commentToDelete.getId());
            Comment comment = (Comment) query.getSingleResult();
            comment.setRepliedComment(null);
            comment.setHasResponse(false);
            commentRepository.save(comment);

            commentToDelete.setReplyToComment(null);
            commentRepository.save(commentToDelete);
        }

        if (commentToDelete.getReplyToComment() != null && commentToDelete.getRepliedComment() != null) {
            Query queryPre = entityManager.createQuery("FROM Comment c WHERE c.repliedComment.id = :repliedCommentId");
            queryPre.setParameter("repliedCommentId", commentToDelete.getId());
            Comment commentPre = (Comment) queryPre.getSingleResult();
            commentPre.setRepliedComment(commentToDelete.getRepliedComment());
            commentRepository.save(commentPre);

            Query queryPost = entityManager.createQuery("FROM Comment c WHERE c.replyToComment.id = :replyCommentId");
            queryPost.setParameter("replyCommentId", commentToDelete.getId());
            Comment commentPost = (Comment) queryPost.getSingleResult();
            commentPost.setReplyToComment(commentToDelete.getReplyToComment());
            commentRepository.save(commentPost);

            commentToDelete.setReplyToComment(null);
            commentToDelete.setRepliedComment(null);
            commentRepository.save(commentToDelete);
        }

        if (commentToDelete.getReplyToComment() == null && commentToDelete.getRepliedComment() != null) {
            Query query = entityManager.createQuery("FROM Comment c WHERE c.id = :repliedCommentId");
            query.setParameter("repliedCommentId", commentToDelete.getRepliedComment().getId());
            Comment comment = (Comment) query.getSingleResult();
            comment.setReplyToComment(null);
            commentRepository.save(comment);

            commentToDelete.setRepliedComment(null);
            commentRepository.save(commentToDelete);
        }

        commentRepository.deleteById(id);
        return commentToDelete;
    }

    @Override
    public Page<Comment> getCommentsByPlace(Place place, Pageable pageable) throws PlaceNotFoundException {
        return commentRepository.findAllCommentsByPlace(place, pageable);
    }

    @Override
    public Page<Comment> getCommentsByUser(User user, Pageable pageable) throws UserNotFoundException {
        return commentRepository.findAllCommentsByUser(user, pageable);
    }

}
