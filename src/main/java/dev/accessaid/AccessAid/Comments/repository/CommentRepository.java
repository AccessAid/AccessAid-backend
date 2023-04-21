package dev.accessaid.AccessAid.Comments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.Comments.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM comment", nativeQuery = true)
    List<Comment> findAllComments();

    Comment findCommentById(Integer id);

    void deleteCommentById(Integer id);

    <S extends Comment> S save(S comment);

}
