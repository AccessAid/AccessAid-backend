package dev.accessaid.AccessAid.Places.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeolocationUtils geolocationUtils;

    @Override
    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public Place findPlaceById(Integer id) throws PlaceNotFoundException {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isPresent()) {
            return placeOptional.get();
        } else {
            throw new PlaceNotFoundException("Place with ID " + id + " not found");
        }
    }

    @Override
    public Place createPlace(PlaceRequest request) throws PlaceSaveException {
        GeolocationResponse response = geolocationUtils.getGeolocationByAddressOrCoordinates(request);
        Place newPlace = new Place(response);
        try {
            placeRepository.save(newPlace);
        } catch (Exception e) {
            throw new PlaceSaveException();
        }
        return newPlace;
    }

    @Override
    public Place removePlace(Integer id) throws PlaceNotFoundException {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isPresent()) {
            placeRepository.deleteById(id);
            return placeOptional.get();
        } else {
            throw new PlaceNotFoundException("Place with ID " + id + " not found");
        }
    }

    @Override
    public List<Place> findPlacesByUser(Integer userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return optionalUser.get().getPlaces();
    }

    @Override
    public List<User> findUsersByPlace(Integer placeId) throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        return optionalPlace.get().getUsers();
    }

    @Override
    public List<Comment> findCommentsByPlace(Integer placeId) throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        return optionalPlace.get().getComments();
    }

    @Override
    public double findTotalRatingByPlace(Integer placeId) throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        return optionalPlace.get().getTotalRating();
    }

    @Override
    public List<Rating> findAllRatingsByPlace(Integer placeId) throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        return optionalPlace.get().getRatings();

    }

}
