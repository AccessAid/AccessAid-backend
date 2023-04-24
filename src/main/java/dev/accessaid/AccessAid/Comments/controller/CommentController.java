package dev.accessaid.AccessAid.Comments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.response.CommentResponse;
import dev.accessaid.AccessAid.Comments.service.CommentServiceImp;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.model.User;
import dev.accessaid.AccessAid.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comments", description = "Comments by the users about the places")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImp commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceServiceImpl placeService;

    @GetMapping("")
    public List<CommentResponse> seeAllComments() {
        List<Comment> comments = commentService.getComments();
        return CommentMapper.toCommentResponses(comments);
    }

    @GetMapping("/{id}")
    public CommentResponse seeCommentById(@PathVariable Integer id) {
        Comment comment = commentService.getCommentById(id);
        return CommentMapper.toCommentResponse(comment);

    }

    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.createComment(comment);
            CommentResponse response = CommentMapper.toCommentResponse(createdComment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody Comment comment) {
        try {
            Comment existingComment = commentService.getCommentById(id);
            if (existingComment == null) {
                ErrorResponse errorResponse = new ErrorResponse("Comment not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            existingComment.setComment(comment.getComment());
            Comment updatedComment = commentService.changeComment(existingComment);
            CommentResponse response = CommentMapper.toCommentResponse(updatedComment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        try {
            Comment commentToDelete = commentService.getCommentById(id);
            if (commentToDelete == null) {
                ErrorResponse errorResponse = new ErrorResponse("Comment not found with id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            commentService.removeComment(id);
            CommentResponse response = CommentMapper.toCommentResponse(commentToDelete);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<?> seeCommentsByPlace(@PathVariable Integer placeId) {
        try {
            Place place = placeService.findPlaceById(placeId);
            if (place == null) {
                ErrorResponse errorResponse = new ErrorResponse("Place not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<Comment> placesComments = commentService.getCommentsByPlace(place);
            if (placesComments.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Place has no comments");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<CommentResponse> response = CommentMapper.toCommentResponses(placesComments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> seeCommentsByUser(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                ErrorResponse errorResponse = new ErrorResponse("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<Comment> comments = commentService.getCommentsByUser(user);
            if (comments.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("User has not commented any place");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<CommentResponse> response = CommentMapper.toCommentResponses(comments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}
