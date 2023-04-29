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
import dev.accessaid.AccessAid.Comments.response.CommentResponse;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.exceptions.PlaceSaveException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Places.utils.PlaceMapper;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;
import dev.accessaid.AccessAid.Ratings.utils.RatingMapper;
import dev.accessaid.AccessAid.User.model.User;
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
            List<PlaceResponse> response = PlaceMapper.toPlaceResponses(places);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> seePlaceById(@PathVariable Integer id) {
        try {
            Place place = placeService.findPlaceById(id);
            PlaceResponse response = PlaceMapper.toPlaceResponse(place);
            return ResponseEntity.ok(response);
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
            PlaceResponse response = PlaceMapper.toPlaceResponse(newPlace);
            return ResponseEntity.ok(response);
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
            PlaceResponse response = PlaceMapper.toPlaceResponse(place);
            return ResponseEntity.ok(response);

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
            List<RatingResponse> response = RatingMapper.toRatingResponses(ratings);
            return ResponseEntity.ok(response);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<?> seeUsersByPlace(@PathVariable Integer id) {
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
    public ResponseEntity<?> seeCommentsByPlace(@PathVariable Integer id) {
        try {
            List<Comment> comments = placeService.findCommentsByPlace(id);
            List<CommentResponse> response = CommentMapper.toCommentResponses(comments);
            return ResponseEntity.ok(response);
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

    @GetMapping("/user/{userid}")
    public ResponseEntity<?> seePlacesByUser(@PathVariable Integer userid) {
        try {
            List<Place> places = placeService.findPlacesByUser(userid);
            List<PlaceResponse> response = PlaceMapper.toPlaceResponses(places);
            return ResponseEntity.ok(response);
        } catch (PlaceNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
