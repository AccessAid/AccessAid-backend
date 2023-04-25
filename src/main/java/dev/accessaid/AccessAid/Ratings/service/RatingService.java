package dev.accessaid.AccessAid.Ratings.service;

import java.util.List;

import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.service.UserNotFoundException;

public interface RatingService {

    List<Rating> getAllRatings();

    Rating getRatingById(Integer id) throws RatingNotFoundException;

    Rating createRating(Rating rating) throws RatingSaveException;

    Rating changeRating(Rating rating) throws RatingNotFoundException, RatingSaveException;

    Rating removeRating(Integer id) throws RatingNotFoundException;

    List<Rating> getRatingByUser(User user) throws UserNotFoundException;

    List<Rating> getRatingByPlace(Place place) throws PlaceNotFoundException;
}
