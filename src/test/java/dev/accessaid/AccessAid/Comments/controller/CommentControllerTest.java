package dev.accessaid.AccessAid.Comments.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.service.CommentServiceImpl;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.User.service.UserServiceImpl;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CommentController commentController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceServiceImpl placeService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CommentServiceImpl commentService;

    private Comment comment;
    private User user;
    private Place place;
    private Comment comment2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        user = User.builder()
                .id(1)
                .username(ExamplesValues.USERNAME)
                .password(ExamplesValues.PASSWORD)
                .email(ExamplesValues.EMAIL)
                .build();
        place = Place.builder()
                .id(1)
                .latitude(Double.parseDouble(ExamplesValues.LATITUDE))
                .longitude(Double.parseDouble(ExamplesValues.LONGITUDE))
                .address(ExamplesValues.ADDRESS)
                .apiPlaceId(ExamplesValues.API_PLACE_ID)
                .totalRating(ExamplesValues.TOTAL_RATING)
                .ratings(new ArrayList<>())
                .build();

        comment = new Comment();

        comment.setUser(user);
        comment.setPlace(place);
        comment.setComment(ExamplesValues.COMMENT);
        comment.setId(1);

        comment2 = new Comment();
        comment2.setUser(user);
        comment2.setPlace(place);
        comment2.setComment(ExamplesValues.COMMENT2);
        comment2.setId(2);

    }

    @DisplayName("It should add a comment")
    @Test
    void testAddComment() throws Exception {

        when(commentService.createComment(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/api/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

    }

    @DisplayName("It should delete a comment")
    @Test
    void testDeleteComment() throws Exception {
       
        when(commentService.removeComment(comment.getId())).thenReturn(null);

        mockMvc.perform(delete("/api/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isNoContent());

    }

    @DisplayName("It should see all comments")
    @Test
    void testSeeAllComments() throws Exception {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);

        when(commentService.getComments()).thenReturn(comments);

        mockMvc.perform(get("/api/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].comment").value(ExamplesValues.COMMENT))
                .andExpect(jsonPath("$[0].user_id").value(user.getId()))
                .andExpect(jsonPath("$[0].place_id").value(place.getId()))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].comment").value(ExamplesValues.COMMENT2))
                .andExpect(jsonPath("$[1].user_id").value(user.getId()))
                .andExpect(jsonPath("$[1].place_id").value(place.getId()));
    }

    @DisplayName("It should see a comment by id")
    @Test
    void testSeeCommentById() throws Exception {

        when(commentService.getCommentById(comment.getId())).thenReturn(comment);

        mockMvc.perform(get("/api/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value(ExamplesValues.COMMENT))
                .andExpect(jsonPath("$.user_id").value(user.getId()))
                .andExpect(jsonPath("$.place_id").value(place.getId()));

    }

    @DisplayName("It should see all comments by place")
    @Test
    void testSeeCommentsByPlace() throws Exception {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);
        when(placeService.findPlaceById(place.getId())).thenReturn(place);
        when(commentService.getCommentsByPlace(place)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/place/{placeId}", place.getId())
                .param("placeId", String.valueOf(place.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].place_id").value(place.getId()))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].place_id").value(place.getId()));

    }

    @DisplayName("It should see all comments by user")
    @Test
    void testSeeCommentsByUser() throws Exception {

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        comments.add(comment2);

        when(userService.getUserById(user.getId())).thenReturn(user);
        when(commentService.getCommentsByUser(user)).thenReturn(comments);

        mockMvc.perform(get("/api/comments/user/{userId}", user.getId())
                .param("userId", String.valueOf(user.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].user_id").value(user.getId()))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].user_id").value(user.getId()));
    }

    @DisplayName("It should update a comment")
    @Test
    void testUpdateComment() throws Exception {

        when(commentService.getCommentById(comment.getId())).thenReturn(comment);
        when(commentService.changeComment(any(Comment.class))).thenReturn(comment);

        comment.setComment(ExamplesValues.COMMENT2);

        mockMvc.perform(put("/api/comments/{id}", comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value(ExamplesValues.COMMENT2))
                .andExpect(jsonPath("$.user_id").value(user.getId()))
                .andExpect(jsonPath("$.place_id").value(place.getId()));

    }
}
