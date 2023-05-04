package dev.accessaid.AccessAid.Ratings.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByPlace(Place place);

    List<Rating> findByUser(User user);
}
