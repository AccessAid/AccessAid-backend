package dev.accessaid.AccessAid.Places.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;

public class PlaceMapper {

    public static PlaceResponse toPlaceResponse(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getLatitude(),
                place.getLongitude(),
                place.getAddress(),
                place.getApi_placeId(),
                place.getTotalRating());
    }

    public static List<PlaceResponse> toPlaceResponses(List<Place> places) {
        return places.stream().map(PlaceMapper::toPlaceResponse).collect(Collectors.toList());
    }

    public static Page<PlaceResponse> toPlaceResponses(Page<Place> places, Pageable pageable) {
        List<PlaceResponse> placeResponses = places.stream()
                .map(PlaceMapper::toPlaceResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(placeResponses, pageable, places.getTotalElements());
    }

}