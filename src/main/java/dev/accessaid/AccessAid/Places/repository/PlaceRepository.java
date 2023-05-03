package dev.accessaid.AccessAid.Places.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query("SELECT p.users FROM Place p WHERE p.id = :placeId")
    List<User> findUsersByPlaceId(@Param("placeId") Integer placeId);

    @Query("SELECT p FROM Place p JOIN p.users u WHERE u.id = :userId")
    List<Place> findPlacesByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Comment c JOIN c.place p WHERE p.id = :placeId")
    List<Comment> findCommentsByPlaceId(@Param("placeId") Integer placeId);

    @Query(value = "SELECT AVG(r.rating) FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    Double findTotalRatingByPlace(Integer placeId);

    @Query(value = "SELECT r FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    List<Rating> findAllRatingsByPlace(Integer placeId);

    Optional<Place> findByLatitudeAndLongitude(double latitude, double longitude);

}
