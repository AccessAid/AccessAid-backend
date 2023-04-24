package dev.accessaid.AccessAid.Places.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.model.User;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    List<User> findUsersById(Integer placeId);

    List<Place> findByUserId(Integer userId);

    List<Comment> findCommentsById(Integer placeId);

    @Query(value = "SELECT AVG(r.rating) FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    Double findTotalRatingByPlace(Integer placeId);

    @Query(value = "SELECT r FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    List<Rating> findAllRatingsByPlace(Integer placeId);

}
