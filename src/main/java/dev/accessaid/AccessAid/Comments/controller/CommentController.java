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
}
