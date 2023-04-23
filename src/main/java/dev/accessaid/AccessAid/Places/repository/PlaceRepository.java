package dev.accessaid.AccessAid.Places.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    // List<Place> findAllPlaces();

    // Place findPlaceById(Integer id);

    // <S extends Place> S save(S place);

    // Place deletePlaceById(Integer id);

    List<User> findUsersByid(Integer id);

    List<Comment> findCommentsByid(Integer id);

}
