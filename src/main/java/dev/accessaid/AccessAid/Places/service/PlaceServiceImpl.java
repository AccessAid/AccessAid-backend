package dev.accessaid.AccessAid.Places.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.TotalRatingResponse;
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

    @Autowired
    private AccessibilityUtils accessibilityUtils;

    @Override
    public Page<Place> findAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable);
    }

    @Override
    public Place findPlaceByAddress(String address) throws PlaceNotFoundException {
        PlaceRequest request = new PlaceRequest();
        request.setAddress(address);
        GeolocationResponse response = geolocationUtils.getGeolocationByAddressOrCoordinates(request);
        Place place = new Place(response);
        return place;
    }

    @Override
    public Place findPlaceByCoordinates(Double lat, Double lng) throws PlaceNotFoundException {
        PlaceRequest request = new PlaceRequest();
        request.setLatitude(lat);
        request.setLongitude(lng);
        GeolocationResponse response = geolocationUtils.getGeolocationByAddressOrCoordinates(request);
        Place place = new Place(response);
        return place;
    }

    @Override
    public Place findPlaceById(Integer id) throws PlaceNotFoundException {
        Optional<Place> placeOptional = placeRepository.findById(id);
        return placeOptional.orElseThrow(() -> new PlaceNotFoundException("Place with ID " + id + " not found"));
    }

    @Override
    public Place createPlace(PlaceRequest request) throws PlaceSaveException {
        GeolocationResponse response = geolocationUtils.getGeolocationByAddressOrCoordinates(request);
        Optional<Place> existingPlace = placeRepository.findByLatitudeAndLongitude(response.getLatitude(),
                response.getLongitude());
        if (existingPlace.isPresent()) {
            throw new PlaceSaveException("Place already exists");
        }
        Place newPlace = new Place(response);

        placeRepository.save(newPlace);
        return newPlace;
    }

    @Override
    public Place removePlace(Integer id) throws PlaceNotFoundException {
        Place placeToRemove = placeRepository.findById(id)
                .orElseThrow(() -> new PlaceNotFoundException("Place with ID " + id + " not found"));
        placeRepository.deleteById(id);
        return placeToRemove;

    }

    @Override
    public Page<Place> findPlacesByUser(Integer userId, Pageable pageable) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<Place> places = user.getPlaces();
        return new PageImpl<>(places, pageable, places.size());

    }

    @Override
    public Page<User> findUsersByPlace(Integer placeId, Pageable pageable)
            throws PlaceNotFoundException, UserNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        List<User> users = optionalPlace.get().getUsers();
        return new PageImpl<>(users, pageable, users.size());

    }

    @Override
    public Page<Comment> findCommentsByPlace(Integer placeId, Pageable pageable)
            throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        List<Comment> comments = optionalPlace.get().getComments();
        return new PageImpl<>(comments, pageable, comments.size());
    }

    @Override
    public TotalRatingResponse findTotalRatingByPlace(Integer placeId) throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        double totalRating = optionalPlace.get().getTotalRating();
        TotalRatingResponse totalRatingResponse = new TotalRatingResponse(placeId, totalRating);
        return totalRatingResponse;
    }

    @Override
    public Page<Rating> findAllRatingsByPlace(Integer placeId, Pageable pageable)
            throws PlaceNotFoundException {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);
        if (!optionalPlace.isPresent()) {
            throw new PlaceNotFoundException("Place not found with id: " + placeId);
        }
        List<Rating> ratings = optionalPlace.get().getRatings();
        return new PageImpl<>(ratings, pageable, ratings.size());

    }

    @Override
    public Place findPlaceByApiPlaceId(String apiPlaceId) {
        return new Place(accessibilityUtils.getGeolocationResponseByApiPlaceId(apiPlaceId));
    }

}
