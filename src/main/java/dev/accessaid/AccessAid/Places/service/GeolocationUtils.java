package dev.accessaid.AccessAid.Places.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.controller.GeolocationController;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;

@Component
public class GeolocationUtils {

    @Autowired
    private GeolocationController geolocationController;

    public GeolocationResponse getGeolocationByAddressOrCoordinates(PlaceRequest request) throws PlaceSaveException {
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
        return response;

    }

    public GeolocationResponse getGeolocationByAddress(String address) throws PlaceSaveException {
        ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByAddress(address);
        if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new PlaceSaveException("Place not found for address: " + address);
        }
        return (GeolocationResponse) geolocationResponseEntity.getBody();
    }

    public GeolocationResponse getGeolocationByCoordinates(double latitude, double longitude)
            throws PlaceSaveException {
        ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByCoordinates(latitude,
                longitude);
        if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK) {
            throw new PlaceSaveException(
                    "Place not found for coordinates: " + latitude + "," + longitude);
        }
        return (GeolocationResponse) geolocationResponseEntity.getBody();

    }
}
