package dev.accessaid.AccessAid.Places.service;

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

    Page<Place> findAllPlaces(Pageable pageable);

    Place findPlaceById(Integer id) throws PlaceNotFoundException;

    Place findPlaceByAddress(String address) throws PlaceNotFoundException;

    Place findPlaceByCoordinates(Double lat, Double lng) throws PlaceNotFoundException;

    Place findPlaceByApiPlaceId(String apiPlaceId) throws PlaceNotFoundException;

    Place createPlace(PlaceRequest request) throws PlaceSaveException;

    Place removePlace(Integer id) throws PlaceNotFoundException;

    Page<Place> findPlacesByUser(Integer userId, Pageable pageable) throws UserNotFoundException;

    Page<User> findUsersByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;

    Page<Comment> findCommentsByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;

    TotalRatingResponse findTotalRatingByPlace(Integer placeId) throws PlaceNotFoundException;

    Page<Rating> findAllRatingsByPlace(Integer placeId, Pageable pageable) throws PlaceNotFoundException;
}
