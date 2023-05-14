package dev.accessaid.AccessAid.Places.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import dev.accessaid.AccessAid.Accessibility.controller.AccessibilityController;
import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;

@Component
public class AccessibilityUtils {

    @Autowired
    AccessibilityController accessibilityController;

    public AccessibilityResponse getPlaceDetailsByPlaceId(String placeId) {
        ResponseEntity<?> placeDetails = accessibilityController.getPlaceDetails(placeId);

        AccessibilityResponse response = (AccessibilityResponse) placeDetails.getBody();

        return response;
    }

};
