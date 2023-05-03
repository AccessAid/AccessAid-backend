package dev.accessaid.AccessAid.Geolocation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Geolocation.service.GeolocationService;
import dev.accessaid.AccessAid.config.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Geolocation", description = "Geolocation information by address or coordinates")
@RestController
public class GeolocationController {

    @Autowired
    private GeolocationService geolocationService;

    @GetMapping("/api/geolocation/byaddress")
    public ResponseEntity<?> getGeolocationByAddress(@RequestParam("address") String address) {
        try {
            GeolocationResponse response = geolocationService.getGeolocationByAddress(address);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/api/geolocation/bycoordinates")
    public ResponseEntity<?> getGeolocationByCoordinates(@RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude) {
        try {
            GeolocationResponse response = geolocationService.getGeolocationByCoordinates(latitude, longitude);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

}