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

import dev.accessaid.AccessAid.Comments.Response.CommentResponse;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.service.CommentServiceImp;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comments", description = "Comments by the users about the places")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImp commentService;

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
    public List<CommentResponse> seeCommentsByPlace(@PathVariable Integer placeId) {
        Place place = new Place();
        place.setId(placeId);
        List<Comment> comments = commentService.getCommentsByPlace(place);
        return CommentMapper.toCommentResponses(comments);
    }

    @GetMapping("/user/{userId}")
    public List<CommentResponse> seeCommentsByUser(@PathVariable Integer userId) {
        User user = new User();
        user.setId(userId);
        List<Comment> comments = commentService.getCommentsByUser(user);
        return CommentMapper.toCommentResponses(comments);
    }
}
