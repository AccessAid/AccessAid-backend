package dev.accessaid.AccessAid.Places.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.controller.GeolocationController;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.utils.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Places.utils.PlaceSaveException;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private GeolocationController geolocationController;

    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    public Place findPlaceById(Integer id) throws PlaceNotFoundException {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isPresent()) {
            return placeOptional.get();
        } else {
            throw new PlaceNotFoundException("Place with ID " + id + " not found");
        }
    }

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

    public Place removePlace(Integer id) throws PlaceNotFoundException {
        Optional<Place> placeOptional = placeRepository.findById(id);
        if (placeOptional.isPresent()) {
            placeRepository.deleteById(id);
            return placeOptional.get();
        } else {
            throw new PlaceNotFoundException("Place with ID " + id + " not found");
        }
    }

}
