package dev.accessaid.AccessAid.Places.service;

import java.util.List;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.utils.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Places.utils.PlaceSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.model.User;
import dev.accessaid.AccessAid.service.UserNotFoundException;

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
