package dev.accessaid.AccessAid.Comments.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    private Comment comment;
    private Comment comment2;
    private User user;
    private Place place;

    @BeforeEach
    void init() {

        user = new User();
        user.setId(1);

        place = new Place();
        place.setId(1);

        comment = new Comment();
        comment.setUser(user);
        comment.setPlace(place);
        comment.setComment(ExamplesValues.COMMENT);
        comment.setId(1);

        comment2 = new Comment();
    }

    @DisplayName("Update existing Comment")
    @Test
    void testChangeComment() {

        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArgument(0));

        Comment updatedComment = commentService.changeComment(comment);

        assertNotNull(updatedComment);
        assertEquals(updatedComment.getComment(), ExamplesValues.COMMENT);
        assertEquals(updatedComment.getUser().getId(), user.getId());
        assertEquals(updatedComment.getPlace().getId(), place.getId());

        updatedComment.setComment(ExamplesValues.COMMENT2);

        when(commentRepository.save(any(Comment.class))).thenAnswer(i -> i.getArgument(0));

        updatedComment = commentService.changeComment(updatedComment);

        assertNotNull(updatedComment);
        assertEquals(updatedComment.getComment(), ExamplesValues.COMMENT2);
        assertEquals(updatedComment.getUser().getId(), user.getId());
        assertEquals(updatedComment.getPlace().getId(), place.getId());

    }

    @DisplayName("Create Comment")
    @Test
    void testCreateComment() {

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(placeRepository.findById(1)).thenReturn(Optional.of(place));
        when(commentRepository.save(comment)).thenReturn(comment);

        place.setComments(new ArrayList<>());
        place.setUsers(new ArrayList<>());

        Comment newComment = commentService.createComment(comment);

        assertNotNull(newComment);
        assertEquals(newComment.getComment(), ExamplesValues.COMMENT);
        assertEquals(newComment.getUser().getId(), user.getId());
        assertEquals(newComment.getPlace().getId(), place.getId());
    }

    @DisplayName("Find a comment by id")
    @Test
    void testGetCommentById() {

        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));

        Comment commentById = commentService.getCommentById(1);

        assertNotNull(commentById);
        assertEquals(commentById.getComment(), ExamplesValues.COMMENT);
        assertEquals(commentById.getUser().getId(), user.getId());
        assertEquals(commentById.getPlace().getId(), place.getId());

    }

    @DisplayName("Find all comments")
    @Test
    void testGetComments() {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> commentsList = commentService.getComments();
        assertNotNull(commentsList);
        assertEquals(commentsList.size(), 2);
    }

    @DisplayName("Find all comments by Place")
    @Test
    void testGetCommentsByPlace() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);
        place.setComments(comments);

        when(commentRepository.findAllCommentsByPlace(place)).thenReturn(comments);

        List<Comment> commentsListByPlace = commentService.getCommentsByPlace(place);
        assertNotNull(commentsListByPlace);
        assertEquals(commentsListByPlace.size(), 2);

    }

    @DisplayName("Find all comments by User")
    @Test
    void testGetCommentsByUser() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);
        user.setComments(comments);

        when(commentRepository.findAllCommentsByUser(user)).thenReturn(comments);

        List<Comment> commentsListByUser = commentService.getCommentsByUser(user);
        assertNotNull(commentsListByUser);
        assertEquals(commentsListByUser.size(), 2);

    }

    @DisplayName("Delete a comment")
    @Test
    void testRemoveComment() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        assertNotNull(comment);
        assertEquals(comment.getId(), 1);

        Comment removedComment = commentService.removeComment(1);

        assertFalse(commentRepository.existsById(removedComment.getId()));
    }
}
