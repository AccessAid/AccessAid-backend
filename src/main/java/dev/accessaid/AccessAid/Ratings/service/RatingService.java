package dev.accessaid.AccessAid.Ratings.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;

public interface RatingService {

    List<Rating> getAllRatings() throws RatingNotFoundException;

    Page<Rating> getAllRatings(Pageable pageable) throws RatingNotFoundException;

    Rating getRatingById(Integer id) throws RatingNotFoundException;

    Rating createRating(Rating rating) throws RatingSaveException;

    Rating changeRating(Rating rating) throws RatingNotFoundException, RatingSaveException;

    Rating removeRating(Integer id) throws RatingNotFoundException;

    List<Rating> getRatingByUser(User user) throws UserNotFoundException;

    Page<Rating> getRatingByUser(User user, Pageable pageable) throws UserNotFoundException;

    List<Rating> getRatingByPlace(Place place) throws PlaceNotFoundException;

    Page<Rating> getRatingByPlace(Place place, Pageable pageable) throws PlaceNotFoundException;
}
