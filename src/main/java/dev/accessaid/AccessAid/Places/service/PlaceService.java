package dev.accessaid.AccessAid.Places.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.TotalRatingResponse;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;

public interface PlaceService {
    List<Place> findAllPlaces() throws PlaceNotFoundException;

    Page<Place> findAllPlaces(Pageable pageable) throws PlaceNotFoundException;

    Place findPlaceById(Integer id) throws PlaceNotFoundException;

    Place createPlace(PlaceRequest request) throws PlaceSaveException;

    Place removePlace(Integer id) throws PlaceNotFoundException;

    List<Place> findPlacesByUser(Integer userId) throws UserNotFoundException;

    Page<Place> findPlacesByUser(Integer userId, Pageable pageable) throws UserNotFoundException;

    List<User> findUsersByPlace(Integer placeId) throws PlaceNotFoundException;

    Page<User> findUsersByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;

    List<Comment> findCommentsByPlace(Integer placeId) throws PlaceNotFoundException;

    Page<Comment> findCommentsByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;

    TotalRatingResponse findTotalRatingByPlace(Integer placeId) throws PlaceNotFoundException;

    List<Rating> findAllRatingsByPlace(Integer placeId) throws PlaceNotFoundException;

    Page<Rating> findAllRatingsByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;
}
