package dev.accessaid.AccessAid.Places.utils;

import java.util.List;
import java.util.stream.Collectors;

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

}