package dev.accessaid.AccessAid.Accessibility.service;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;

@Service
public class AccessibilityService {

    private final GeoApiContext context;

    public AccessibilityService(@Value("${spring.sendgrid.api-key}") String apiKey) {
        this.context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public AccessibilityResponse getPlaceDetails(String placeId) throws Exception {
        PlaceDetails details = PlacesApi.placeDetails(context, placeId).await();

        String name = details.name;
        URL url = details.url;
        Boolean wheelchair_accessible_entrance = details.wheelchairAccessibleEntrance;
        URL website = details.website;
        AddressType[] types = details.types;
        Photo[] photos = details.photos;

        AccessibilityResponse response = new AccessibilityResponse(name, url, wheelchair_accessible_entrance, website,
                types, photos);
        return response;
    }

}
