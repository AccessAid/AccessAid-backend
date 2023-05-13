package dev.accessaid.AccessAid.Accessibility.service;

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

        String name = details.name != null ? details.name : null;
        String phone = details.formattedPhoneNumber != null ? details.formattedPhoneNumber : null;
        String url = details.url != null ? details.url.toString() : null;
        Boolean wheelchair_accessible_entrance = details.wheelchairAccessibleEntrance != null
                ? details.wheelchairAccessibleEntrance
                : null;
        String website = details.website != null ? details.website.toString() : null;
        AddressType[] types = details.types != null ? details.types : null;
        Photo[] photos = details.photos != null ? details.photos : null;

        AccessibilityResponse response = new AccessibilityResponse(name, phone, url, wheelchair_accessible_entrance,
                website,
                types, photos);
        return response;
    }

}
