package dev.accessaid.AccessAid.Places.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;

public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query("SELECT p.users FROM Place p WHERE p.id = :placeId")
    List<User> findUsersByPlaceId(@Param("placeId") Integer placeId);

    @Query("SELECT p.users FROM Place p WHERE p.id = :placeId")
    Page<User> findUsersByPlaceId(@Param("placeId") Integer placeId, Pageable pageable);

    @Query("SELECT p FROM Place p JOIN p.users u WHERE u.id = :userId")
    List<Place> findPlacesByUserId(@Param("userId") Integer userId);

    @Query("SELECT p FROM Place p JOIN p.users u WHERE u.id = :userId")
    Page<Place> findPlacesByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT c FROM Comment c JOIN c.place p WHERE p.id = :placeId")
    List<Comment> findCommentsByPlaceId(@Param("placeId") Integer placeId);

    @Query("SELECT c FROM Comment c JOIN c.place p WHERE p.id = :placeId")
    Page<Comment> findCommentsByPlaceId(@Param("placeId") Integer placeId, Pageable pageable);

    @Query(value = "SELECT AVG(r.rating) FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    Double findTotalRatingByPlace(Integer placeId);

    @Query(value = "SELECT r FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    List<Rating> findAllRatingsByPlace(Integer placeId);

    @Query(value = "SELECT r FROM Place p JOIN p.ratings r WHERE p.id = ?1")
    Page<Rating> findAllRatingsByPlace(Integer placeId, Pageable pageable);

    Optional<Place> findByLatitudeAndLongitude(double latitude, double longitude);

}
