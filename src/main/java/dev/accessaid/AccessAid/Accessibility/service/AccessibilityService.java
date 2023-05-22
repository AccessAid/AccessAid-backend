package dev.accessaid.AccessAid.Accessibility.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.PlacesApi;
import com.google.maps.model.AddressType;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.Photo;
import com.google.maps.model.PlaceDetails;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Accessibility.response.NearbyPlacesResponse;
import dev.accessaid.AccessAid.Accessibility.response.PlaceAndNearbyPlacesResponse;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Places.service.GeolocationUtils;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Service
public class AccessibilityService {

        @Value("${spring.sendgrid.api-key}")
        String apiKey;

        @Autowired
        private GeolocationUtils geolocationUtils;

        private final GeoApiContext context;

        public AccessibilityService(@Value("${spring.sendgrid.api-key}") String apiKey) {
                this.context = new GeoApiContext.Builder()
                                .apiKey(apiKey)
                                .build();
        }

        public AccessibilityResponse getPlaceDetails(String placeId) throws Exception {

                PlaceDetailsRequest request = PlacesApi.placeDetails(context, placeId);

                request.fields(Arrays.stream(PlaceDetailsRequest.FieldMask.values())
                                .filter(x -> x != PlaceDetailsRequest.FieldMask.SECONDARY_OPENING_HOURS)
                                .toArray(PlaceDetailsRequest.FieldMask[]::new));

                PlaceDetails details = request.await();

                String name = details.name != null ? details.name : null;
                String phone = details.formattedPhoneNumber != null ? details.formattedPhoneNumber : null;
                String url = details.url != null ? details.url.toString() : null;
                Boolean wheelchair_accessible_entrance = details.wheelchairAccessibleEntrance != null
                                ? details.wheelchairAccessibleEntrance
                                : null;
                String website = details.website != null ? details.website.toString() : null;
                AddressType[] types = details.types != null ? details.types : null;
                Photo[] photos = details.photos != null ? details.photos : null;
                OpeningHours openingHours = details.openingHours != null ? details.openingHours : null;

                AccessibilityResponse response = new AccessibilityResponse(name, phone, url,
                                wheelchair_accessible_entrance,
                                openingHours,
                                website,
                                types, photos);
                return response;
        }

        public PlaceAndNearbyPlacesResponse getNearbyPlaces(Double lat, Double lng, String address, String type)
                        throws Exception {

                PlaceRequest placeRequest = new PlaceRequest(address, lat, lng);

                GeolocationResponse geolocationResponse = geolocationUtils
                                .getGeolocationByAddressOrCoordinates(placeRequest);

                double latitud = geolocationResponse.getLatitude();
                double longitude = geolocationResponse.getLongitude();

                String addressType = type == null ? "" : type.toLowerCase();
                Response response = NearbyPlaceUtils.getResponse(latitud, longitude, addressType, apiKey);

                AccessibilityResponse accessibilityResponse = getPlaceDetails(geolocationResponse.getPlaceId());

                ResponseBody responseBody = response.body();
                String json = responseBody != null ? responseBody.string() : "";

                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                JsonElement results = jsonObject.get("results");
                JsonElement status = jsonObject.get("status");
                if (!status.getAsString().equals("OK")) {
                        throw new Exception(status.getAsString());

                }

                String next_page_token = jsonObject.get("next_page_token") != null
                                ? jsonObject.get("next_page_token").getAsString()
                                : null;

                List<NearbyPlacesResponse> nearbyPlaces = NearbyPlaceUtils.getNearbyPlaces(results);

                PlaceAndNearbyPlacesResponse placeAndNearbyPlacesResponse = new PlaceAndNearbyPlacesResponse(
                                accessibilityResponse, nearbyPlaces, next_page_token);

                return placeAndNearbyPlacesResponse;
        }

        public GeolocationResponse getGeolocationResponse(String placeId) throws Exception {

                PlaceDetailsRequest request = PlacesApi.placeDetails(context, placeId);

                request.fields(Arrays.stream(PlaceDetailsRequest.FieldMask.values())
                                .filter(x -> x != PlaceDetailsRequest.FieldMask.SECONDARY_OPENING_HOURS)
                                .toArray(PlaceDetailsRequest.FieldMask[]::new));

                PlaceDetails details = request.await();

                double latitud = details.geometry.location.lat;
                double longitude = details.geometry.location.lng;
                String formattedAddress = details.formattedAddress != null ? details.formattedAddress : null;

                GeolocationResponse response = new GeolocationResponse(latitud, longitude, formattedAddress,
                                placeId);
                return response;
        }

}