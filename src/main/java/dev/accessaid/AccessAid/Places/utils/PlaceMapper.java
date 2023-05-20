package dev.accessaid.AccessAid.Places.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import dev.accessaid.AccessAid.Places.service.AccessibilityUtils;

@Component
public class PlaceMapper {

    @Autowired
    private AccessibilityUtils accessibilityUtils;

    public PlaceResponse toPlaceResponse(Place place) {

        String apiPlaceId = place.getApiPlaceId();
        AccessibilityResponse placeDetails = accessibilityUtils.getPlaceDetailsByPlaceId(apiPlaceId);
        return new PlaceResponse(
                place.getId(),
                place.getLatitude(),
                place.getLongitude(),
                place.getAddress(),
                apiPlaceId,
                place.getTotalRating(),
                placeDetails);
    }

    public List<PlaceResponse> toPlaceResponses(List<Place> places) {
        return places.stream()
                .map(place -> toPlaceResponse(place))
                .collect(Collectors.toList());
    }

    public Page<PlaceResponse> toPlaceResponses(Page<Place> places, Pageable pageable) {
        List<PlaceResponse> placeResponses = places.stream()
                .map(place -> toPlaceResponse(place))
                .collect(Collectors.toList());
        return new PageImpl<>(placeResponses, pageable, places.getTotalElements());
    }

}