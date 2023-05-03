package dev.accessaid.AccessAid.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RequestPlacesAPI {
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place";
    private static final String PLACES_API_KEY = "AIzaSyC3HiLD81Addh-2evqNwvFzE4UOzHkolSQ";

    public static String getAccesibilityInfo(double latitude, double longitude){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PLACES_API_URL + "/nearbysearch/json")
        .queryParam("location", latitude + "," + longitude)
        .queryParam("rankby", "distance")
        .queryParam("type", "point_of_interest")
        .queryParam("key", PLACES_API_KEY)
        .queryParam("fields", "accessibility");
    
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(builder.toUriString(), String.class);

        return response;

    }
}
