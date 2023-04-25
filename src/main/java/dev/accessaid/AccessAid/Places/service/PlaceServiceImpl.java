package dev.accessaid.AccessAid.Places.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.controller.GeolocationController;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.User.service.UserNotFoundException;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GeolocationController geolocationController;

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
        GeolocationResponse response;
        String address = request.getAddress();
        if (address == null && (request.getLatitude() == null && request.getLongitude() == null)) {
            throw new PlaceSaveException("Missing address or coordinates");
        } else if (address != null) {
            ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByAddress(address);
            if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK) {
                throw new PlaceSaveException("Place not found for address: " + address);
            }
            response = (GeolocationResponse) geolocationResponseEntity.getBody();

        } else {
            double latitude = request.getLatitude();
            double longitude = request.getLongitude();
            ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByCoordinates(latitude,
                    longitude);
            if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK) {
                throw new PlaceSaveException(
                        "Place not found for coordinates: " + latitude + "," + longitude);
            }
            response = (GeolocationResponse) geolocationResponseEntity.getBody();
        }
        Place newPlace = new Place(response);
        try {
            placeRepository.save(newPlace);
        } catch (Exception e) {
            throw new PlaceSaveException("Error saving place to database");
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
