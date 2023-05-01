package dev.accessaid.AccessAid.Comments.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id(1)
                .username(ExamplesValues.USERNAME)
                .password(ExamplesValues.PASSWORD)
                .email(ExamplesValues.EMAIL)
                .build();
        user = userRepository.save(user);

        Place place = Place.builder()
                .id(1)
                .latitude(Double.parseDouble(ExamplesValues.LATITUDE))
                .longitude(Double.parseDouble(ExamplesValues.LONGITUDE))
                .address(ExamplesValues.ADDRESS)
                .api_placeId(ExamplesValues.API_PLACE_ID)
                .totalRating(ExamplesValues.TOTAL_RATING)
                .build();
        place = placeRepository.save(place);
    }

    @Test
    @DisplayName("Save a comment in the database")
    void testSaveComment() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertNotNull(savedComment);
        assertNotNull(savedComment.getId());
        assertEquals(savedComment.getComment(), ExamplesValues.COMMENT);
        assertEquals(savedComment.getUser(), user);
        assertEquals(savedComment.getPlace(), place);

    }

    @Test
    @DisplayName("Return all comments from the database")
    void testFindAllComments() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        comment = commentRepository.save(comment);
        Comment comment2 = Comment.builder()
                .comment(ExamplesValues.COMMENT2)
                .user(user)
                .place(place)
                .build();

        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();
        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    @DisplayName("Return a comment by Id from the database")
    void testFindCommentById() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        comment = commentRepository.save(comment);
        Comment commentFound = commentRepository.findById(comment.getId()).orElse(null);
        assertNotNull(commentFound);
        assertEquals(commentFound.getComment(), ExamplesValues.COMMENT);
        assertEquals(commentFound.getUser().getId(), 1);
        assertEquals(commentFound.getPlace().getId(), 1);
    }

    @Test
    @DisplayName("Save a comment in the database")
    void testUpdateComment() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();
        Comment commentToUpdate = commentRepository.save(comment);
        commentToUpdate.setComment(ExamplesValues.COMMENT2);

        Comment savedComment = commentRepository.save(commentToUpdate);

        assertNotNull(savedComment);
        assertNotNull(savedComment.getId());
        assertEquals(savedComment.getComment(), ExamplesValues.COMMENT2);

    }

    @Test
    @DisplayName("Delete a comment by Id from the database")
    @Transactional
    void testDeleteCommentById() {

        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        comment = commentRepository.save(comment);
        Comment comment2 = Comment.builder()
                .comment(ExamplesValues.COMMENT2)
                .user(user)
                .place(place)
                .build();

        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAll();
        assertEquals(2, comments.size());

        commentRepository.deleteById(comment.getId());

        commentRepository.flush();
        Comment deletedComment = commentRepository.findById(comment.getId()).orElse(null);

        List<Comment> newList = commentRepository.findAll();
        assertNull(deletedComment);
        assertEquals(1, newList.size());
    }

    @Test
    @DisplayName("Return all comments by place from the database")
    void testFindAllCommentsByPlace() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        comment = commentRepository.save(comment);
        Comment comment2 = Comment.builder()
                .comment(ExamplesValues.COMMENT2)
                .user(user)
                .place(place)
                .build();

        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAllCommentsByPlace(place);
        assertEquals(comments.size(), 2);
        assertEquals(comments.get(0).getComment(), ExamplesValues.COMMENT);
        assertEquals(comments.get(1).getComment(), ExamplesValues.COMMENT2);
        assertEquals(comments.get(0).getPlace(), place);
        assertEquals(comments.get(1).getPlace(), place);

    }

    @Test
    @DisplayName("Return all comments by user from the database")
    void testFindAllCommentsByUser() {
        User user = userRepository.findById(1).orElse(null);
        Place place = placeRepository.findById(1).orElse(null);
        Comment comment = Comment.builder()
                .comment(ExamplesValues.COMMENT)
                .user(user)
                .place(place)
                .build();

        comment = commentRepository.save(comment);
        Comment comment2 = Comment.builder()
                .comment(ExamplesValues.COMMENT2)
                .user(user)
                .place(place)
                .build();

        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findAllCommentsByUser(user);
        assertEquals(comments.size(), 2);
        assertEquals(comments.get(0).getComment(), ExamplesValues.COMMENT);
        assertEquals(comments.get(1).getComment(), ExamplesValues.COMMENT2);
        assertEquals(comments.get(0).getUser(), user);
        assertEquals(comments.get(1).getUser(), user);

    }

}
