package dev.accessaid.AccessAid.Places.service;

import java.util.List;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;

public interface PlaceService {
    List<Place> findAllPlaces();

    Place findPlaceById(Integer id) throws PlaceNotFoundException;

    Place createPlace(PlaceRequest request) throws PlaceSaveException;

    Place removePlace(Integer id) throws PlaceNotFoundException;

    List<Place> findPlacesByUser(Integer userId) throws UserNotFoundException;

    List<User> findUsersByPlace(Integer placeId) throws PlaceNotFoundException;

    List<Comment> findCommentsByPlace(Integer placeId) throws PlaceNotFoundException;

    double findTotalRatingByPlace(Integer placeId) throws PlaceNotFoundException;

    List<Rating> findAllRatingsByPlace(Integer placeId) throws PlaceNotFoundException;
}
