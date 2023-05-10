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
        String address = request.getAddress();
        Double latitude = request.getLatitude();
        Double longitude = request.getLongitude();

        if (address == null && latitude == null && longitude == null)
            throw new PlaceSaveException("Missing address or coordinates");

        if (address != null) {
            ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByAddress(address);
            if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK)
                throw new PlaceSaveException("Place not found for address: " + address);
            GeolocationResponse response = (GeolocationResponse) geolocationResponseEntity.getBody();
            return response;
        }

        if (address == null && latitude == null && longitude != null)
            throw new PlaceSaveException("Missing address or latitude");

        if (address == null && latitude != null && longitude == null)
            throw new PlaceSaveException("Missing address or longitude");

        if (address == null && latitude != null & longitude != null) {
            ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByCoordinates(latitude, longitude);
            if (geolocationResponseEntity.getStatusCode() != HttpStatus.OK) {
                throw new PlaceSaveException(
                        "Place not found for coordinates: " + latitude + "," + longitude);
            }
        }

        ResponseEntity<?> geolocationResponseEntity = geolocationController.getGeolocationByCoordinates(latitude, longitude);
        GeolocationResponse response = (GeolocationResponse) geolocationResponseEntity.getBody();
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
