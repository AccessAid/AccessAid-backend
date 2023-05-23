package dev.accessaid.AccessAid.Geolocation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;

@Service
public class GeolocationService {

    private final GeoApiContext context;

    public GeolocationService(@Value("${GOOGLE_APIKEY}") String apiKey) {
        this.context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    public GeolocationResponse getGeolocationByAddress(String address) throws Exception {

        GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
        if (results.length > 0) {
            double latitude = results[0].geometry.location.lat;
            double longitude = results[0].geometry.location.lng;
            String formattedAddress = results[0].formattedAddress;
            String placeId = results[0].placeId;

            GeolocationResponse response = new GeolocationResponse(latitude, longitude, formattedAddress, placeId);
            return response;
        } else {
            throw new Exception("No results found");
        }
    }

    public GeolocationResponse getGeolocationByCoordinates(double latitude, double longitude) throws Exception {

        GeocodingResult[] results = GeocodingApi.reverseGeocode(context, new LatLng(latitude, longitude)).await();
        if (results.length > 0) {
            String formattedAddress = results[0].formattedAddress;
            String placeId = results[0].placeId;

            GeolocationResponse response = new GeolocationResponse(latitude, longitude, formattedAddress, placeId);
            return response;

        } else {
            throw new Exception("No results found");
        }

    }

}
