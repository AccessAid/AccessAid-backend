package dev.accessaid.AccessAid.Places.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import dev.accessaid.AccessAid.Accessibility.controller.AccessibilityController;
import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Accessibility.service.AccessibilityService;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;

@Component
public class AccessibilityUtils {

    @Autowired
    AccessibilityController accessibilityController;

    @Autowired
    AccessibilityService accessibilityService;

    public AccessibilityResponse getPlaceDetailsByPlaceId(String placeId) {
        ResponseEntity<?> placeDetails = accessibilityController.getPlaceDetails(placeId);

        AccessibilityResponse response = (AccessibilityResponse) placeDetails.getBody();

        return response;
    };

    public GeolocationResponse getGeolocationResponseByApiPlaceId(String placeId) {
        try {
            GeolocationResponse response = accessibilityService.getGeolocationResponse(placeId);
            return response;
        } catch (Exception e) {
            throw new PlaceNotFoundException("no geolocation found");
        }
    }
};
