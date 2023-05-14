package dev.accessaid.AccessAid.Places.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.response.CommentResponse;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Places.utils.PlaceMapper;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;
import dev.accessaid.AccessAid.Ratings.response.TotalRatingResponse;
import dev.accessaid.AccessAid.Ratings.utils.RatingMapper;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.response.UserResponse;
import dev.accessaid.AccessAid.User.utils.UserMapper;
import dev.accessaid.AccessAid.config.documentation.Comments.CommentResponseExample;
import dev.accessaid.AccessAid.config.documentation.Places.PlaceRequestExample;
import dev.accessaid.AccessAid.config.documentation.Places.PlaceResponseExample;
import dev.accessaid.AccessAid.config.documentation.Ratings.RatingResponseExample;
import dev.accessaid.AccessAid.config.documentation.Ratings.TotalRatingResponseExample;
import dev.accessaid.AccessAid.config.documentation.Users.UserResponseExample;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Places", description = "Information about places")
@RestController
@RequestMapping("/api/places")
public class PlaceController {

    @Autowired
    private PlaceServiceImpl placeService;

    @Autowired
    private PlaceMapper placeMapper;

    @Operation(summary = "See a list of places")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @Hidden
    @GetMapping("/unpaged")
    public List<PlaceResponse> seeAllPlaces() {
        List<Place> places = placeService.findAllPlaces();
        return placeMapper.toPlaceResponses(places);
    }

    @Operation(summary = "See a list of places")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public Page<PlaceResponse> seeAllPlaces(Pageable pageable) {
        Page<Place> places = placeService.findAllPlaces(pageable);
        return placeMapper.toPlaceResponses(places, pageable);
    }

    @Operation(summary = "See a place by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Place found", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponse> seePlaceById(@PathVariable Integer id) {
        Place place = placeService.findPlaceById(id);
        PlaceResponse response = placeMapper.toPlaceResponse(place);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new place to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Place added successfully", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
            @ApiResponse(responseCode = "400", description = "Error saving place", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceResponse addPlace(
            @RequestBody @Validated @Schema(oneOf = { PlaceRequestExample.class }) PlaceRequest request) {
        Place newPlace = placeService.createPlace(request);
        return placeMapper.toPlaceResponse(newPlace);
    }

    @Operation(summary = "Delete a place by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deletePlace(@PathVariable Integer id) throws PlaceNotFoundException {
        Place place = placeService.findPlaceById(id);
        placeService.removePlace(id);
        PlaceResponse response = placeMapper.toPlaceResponse(place);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See all ratings for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @Hidden
    @GetMapping("/{id}/ratings/unpaged")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RatingResponse>> seePlaceRatings(@PathVariable Integer id) {
        List<Rating> ratings = placeService.findAllRatingsByPlace(id);
        List<RatingResponse> response = RatingMapper.toRatingResponses(ratings);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See all ratings for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @GetMapping("/{id}/ratings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<RatingResponse>> seePlaceRatings(@PathVariable Integer id, Pageable pageable) {
        Page<Rating> ratings = placeService.findAllRatingsByPlace(id, pageable);
        Page<RatingResponse> response = RatingMapper.toRatingResponses(ratings, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See users that have rated or commented a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @Hidden
    @GetMapping("/{id}/users/unpaged")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> seeUsersByPlace(@PathVariable Integer id) {
        List<User> users = placeService.findUsersByPlace(id);
        List<UserResponse> response = UserMapper.toUserResponses(users);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See users that have rated or commented a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @GetMapping("/{id}/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> seeUsersByPlace(@PathVariable Integer id, Pageable pageable) {
        Page<User> users = placeService.findUsersByPlace(id, pageable);
        Page<UserResponse> response = UserMapper.toUserResponses(users, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See the comments for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
    })
    @Hidden
    @GetMapping("/{id}/comments/unpaged")
    public List<CommentResponse> seeCommentsByPlace(@PathVariable Integer id) {
        List<Comment> comments = placeService.findCommentsByPlace(id);
        return CommentMapper.toCommentResponses(comments);
    }

    @Operation(summary = "See the comments for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
    })
    @GetMapping("/{id}/comments")
    public Page<CommentResponse> seeCommentsByPlace(@PathVariable Integer id, Pageable pageable) {
        Page<Comment> comments = placeService.findCommentsByPlace(id, pageable);
        return CommentMapper.toCommentResponses(comments, pageable);
    }

    @Operation(summary = "See total rating for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TotalRatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @GetMapping("/{id}/totalRating")
    public TotalRatingResponse seeTotalRating(@PathVariable Integer id) {
        return placeService.findTotalRatingByPlace(id);
    }

    @Operation(summary = "See places where a user has commented or rated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @Hidden
    @GetMapping("/user/{userid}/unpaged")
    public ResponseEntity<List<PlaceResponse>> seePlacesByUser(@PathVariable Integer userid) {
        List<Place> places = placeService.findPlacesByUser(userid);
        List<PlaceResponse> response = placeMapper.toPlaceResponses(places);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "See places where a user has commented or rated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlaceResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
    })
    @GetMapping("/user/{userid}")
    public ResponseEntity<Page<PlaceResponse>> seePlacesByUser(@PathVariable Integer userid, Pageable pageable) {
        Page<Place> places = placeService.findPlacesByUser(userid, pageable);
        Page<PlaceResponse> response = placeMapper.toPlaceResponses(places, pageable);
        return ResponseEntity.ok(response);
    }

}
