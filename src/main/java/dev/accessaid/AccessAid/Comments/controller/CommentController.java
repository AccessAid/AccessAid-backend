package dev.accessaid.AccessAid.Comments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.exceptions.CommentNotFoundException;
import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.response.CommentResponse;
import dev.accessaid.AccessAid.Comments.service.CommentServiceImpl;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.config.documentation.Comments.CommentRequestExample;
import dev.accessaid.AccessAid.config.documentation.Comments.CommentResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comments", description = "Comments by the users about the places")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceServiceImpl placeService;

    @Operation(summary = "See a list of comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public Page<CommentResponse> seeAllComments(Pageable pageable) {
        return CommentMapper.toCommentResponses(commentService.getComments(pageable), pageable);
    }

    @Operation(summary = "See a comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommentResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content)
    })
    @GetMapping("/{id}")
    public CommentResponse seeCommentById(@PathVariable Integer id) {
        return CommentMapper.toCommentResponse(commentService.getCommentById(id));
    }

    @Operation(summary = "Add comment", description = "Create a comment for a place", tags = { "Comments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = CommentResponseExample.class))),
            @ApiResponse(responseCode = "400", description = "Error saving comment", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addComment(
            @RequestBody @Validated @Schema(implementation = CommentRequestExample.class) Comment comment) {
        return CommentMapper.toCommentResponse(commentService.createComment(comment));
    }

    @Operation(summary = "Update an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(schema = @Schema(implementation = CommentResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Integer id,
            @RequestBody @Schema(example = "{\"comment\": \"Nice Place!\"}") Comment comment) {
        Comment existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            throw new CommentNotFoundException("Comment not found with id: " + id);
        }
        existingComment.setComment(comment.getComment());
        Comment updatedComment = commentService.changeComment(existingComment);
        return CommentMapper.toCommentResponse(updatedComment);

    }

    @Operation(summary = "Delete an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Integer id) {
        commentService.removeComment(id);
    }

    @Operation(summary = "See all comments that have been made about a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Comments not found", content = @Content)
    })
    @GetMapping("/place/{placeId}")
    public Page<CommentResponse> seeCommentsByPlace(@PathVariable Integer placeId,
            Pageable pageable) {
        return CommentMapper.toCommentResponses(commentService.getCommentsByPlace(placeService.findPlaceById(placeId), pageable), pageable);
    }

    @Operation(summary = "See all comments that a user has made")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Comments not found", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public Page<CommentResponse> seeCommentsByUser(@PathVariable Integer userId,
            Pageable pageable) {
        return CommentMapper.toCommentResponses(commentService.getCommentsByUser(userService.getUserById(userId), pageable), pageable);
    }

}
