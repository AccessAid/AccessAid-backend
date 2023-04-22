package dev.accessaid.AccessAid.Comments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.service.CommentService;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Comments", description = "Comments by the users about the places")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public List<Comment> seeAllComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public Comment seeCommentById(@PathVariable Integer id) {
        return commentService.getCommentById(id);
    }

    @PostMapping("")
    public Comment addComment(@RequestBody Comment comment) {

        return commentService.createComment(comment);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Integer id, @RequestBody Comment comment) {
        return commentService.changeComment(comment);
    }

    @DeleteMapping("/{id}")
    public Comment deleteComment(@PathVariable Integer id) {
        return commentService.removeComment(id);
    }

    @GetMapping("/place/{placeId}")
    public List<Comment> seeCommentsByPlace(@PathVariable Integer placeId) {
        Place place = new Place();
        place.setId(placeId);
        return commentService.getAllCommentsByPlace(place);
    }

    @GetMapping("/user/{userId}")
    public List<Comment> seeCommentsByUser(@PathVariable Integer userId) {
        User user = new User();
        user.setId(userId);
        return commentService.getAllCommentsByUser(user);
    }
}
