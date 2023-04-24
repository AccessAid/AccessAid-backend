package dev.accessaid.AccessAid.Places.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Places.utils.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Places.utils.PlaceSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Places", description = "Information about places")
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private PlaceServiceImpl placeService;

    @GetMapping("")
    public ResponseEntity<?> seeAllPlaces() {
        try {
            List<Place> places = placeService.findAllPlaces();
            return ResponseEntity.ok(places);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> seePlaceById(@PathVariable Integer id) {
        try {
            Place place = placeService.findPlaceById(id);
            return ResponseEntity.ok(place);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> addPlace(@RequestBody PlaceRequest request) {
        try {
            Place newPlace = placeService.createPlace(request);
            return ResponseEntity.ok(newPlace);
        } catch (PlaceSaveException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable Integer id) {
        try {
            Place place = placeService.findPlaceById(id);
            placeService.removePlace(id);
            return ResponseEntity.ok(place);

        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}/ratings")
    public ResponseEntity<?> seePlaceRatings(@PathVariable Integer id) {
        try {
            List<Rating> ratings = placeService.findAllRatingsByPlace(id);
            return ResponseEntity.ok(ratings);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<?> seePlaceUsers(@PathVariable Integer id) {
        try {
            List<User> users = placeService.findUsersByPlace(id);
            return ResponseEntity.ok(users);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> seePlaceComments(@PathVariable Integer id) {
        try {
            List<Comment> comments = placeService.findCommentsByPlace(id);
            return ResponseEntity.ok(comments);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}/totalRating")
    public ResponseEntity<?> seeTotalRating(@PathVariable Integer id) {
        try {
            double totalRating = placeService.findTotalRatingByPlace(id);
            return ResponseEntity.ok(totalRating);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> seePlacesByUser(@PathVariable Integer userid) {
        try {
            List<Place> places = placeService.findPlacesByUser(userid);
            return ResponseEntity.ok(places);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
