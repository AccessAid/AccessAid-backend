package dev.accessaid.AccessAid.Ratings.service;

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
    Page<Rating> getAllRatings(Pageable pageable) throws RatingNotFoundException;

    Rating getRatingById(Integer id) throws RatingNotFoundException;

    Rating createRating(Rating rating) throws RatingSaveException;

    Rating changeRating(Integer id, Rating rating) throws RatingNotFoundException, RatingSaveException;

    Rating removeRating(Integer id) throws RatingNotFoundException;

    Page<Rating> getRatingByUser(User user, Pageable pageable) throws UserNotFoundException;

    Page<Rating> getRatingByPlace(Place place, Pageable pageable) throws PlaceNotFoundException;
}
