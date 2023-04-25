package dev.accessaid.AccessAid.Ratings.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;



public interface RatingService {

    List<Rating> getAllRatings();

    Rating getRatingById(Integer id) throws RatingNotFoundException;

    Rating createRating(Rating rating) throws RatingSaveException;

    Rating changeRating(Rating rating) throws RatingNotFoundException;

    Rating removeRating(Integer id) throws RatingNotFoundException;

    List<Rating> getRatingByUser(User user) throws UsernameNotFoundException;

    List<Rating> getRatingByPlace(Place place) throws PlaceNotFoundException;
}
