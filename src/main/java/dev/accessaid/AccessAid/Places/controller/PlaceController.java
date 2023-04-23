package dev.accessaid.AccessAid.Places.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.controller.GeolocationController;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.repository.PlaceRepository;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Places", description = "Information about places")
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private GeolocationController geolocationController;

    @Autowired
    private PlaceRepository placeRepository;

    @GetMapping("")
    public String getPlaces() {
        return "Place";
    }

    @PostMapping("")
    public ResponseEntity<?> createPlace(@RequestBody PlaceRequest request) {
        try {
            GeolocationResponse response;
            String address = request.getAddress();
            if (address == null && (request.getLatitude() == null && request.getLongitude() == null)) {
                ErrorResponse errorResponse = new ErrorResponse("Missing address or coordinates");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            } else if (address != null) {
                response = (GeolocationResponse) geolocationController.getGeolocationByAddress(address).getBody();

            } else {
                double latitude = request.getLatitude();
                double longitude = request.getLongitude();
                response = (GeolocationResponse) geolocationController.getGeolocationByCoordinates(latitude, longitude)
                        .getBody();
            }
            Place newPlace = new Place(response);
            placeRepository.save(newPlace);

            return ResponseEntity.ok(newPlace);

        } catch (Exception e) {
            System.out.println(e);
            ErrorResponse errorResponse = new ErrorResponse("Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public String updatePlace(@RequestBody Place place) {
        return "Place";
    }

    @DeleteMapping("/{id}")
    public String deletePlace(@RequestBody Place place) {
        return "Place";
    }
}
