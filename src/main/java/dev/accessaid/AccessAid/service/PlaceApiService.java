package dev.accessaid.AccessAid.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;

import dev.accessaid.AccessAid.model.PlaceModel;
import dev.accessaid.AccessAid.repository.PlaceRepository;

@Service
public class PlaceApiService {
    
    @Autowired
    private PlaceRepository placeRepository;

    public void savePlaceFromApi(String placeId){
        /*Code to get the place data from the Places API*/
        

        /*Collected data*/
        PlaceModel place = new PlaceModel(placeId, placeId, placeId, 0, 0);
        place.setId(placeId);
        place.setName("Nombre del lugar");
        place.setAddress("Dirección del lugar");
        
        LatLng latLng = place.getLatLng();
        double latitude = latLng.lat;
        double longitude = latLng.lng;

        placeRepository.save(place);
    }

    private GeoApiContext geoApiContext = new GeoApiContext.Builder()
        .apiKey("AIzaSyC3HiLD81Addh-2evqNwvFzE4UOzHkolSQ")
        .build();

    /*request*/
    public PlaceDetails getPlaceDetails(String placeId) throws Exception{
        PlaceDetails placeDetails = PlacesApi.placeDetails(geoApiContext, placeId).await();
        return placeDetails;
    }

}