package dev.accessaid.AccessAid.Places.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Comments.response.CommentResponse;
import dev.accessaid.AccessAid.Comments.utils.CommentMapper;
import dev.accessaid.AccessAid.Places.exceptions.PlaceNotFoundException;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Places.utils.PlaceMapper;
import dev.accessaid.AccessAid.Places.utils.PlaceRequest;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;
import dev.accessaid.AccessAid.Ratings.response.TotalRatingResponse;
import dev.accessaid.AccessAid.Ratings.utils.RatingMapper;
import dev.accessaid.AccessAid.User.utils.UserMapper;
import dev.accessaid.AccessAid.config.documentation.Comments.CommentResponseExample;
import dev.accessaid.AccessAid.config.documentation.Places.PlaceRequestExample;
import dev.accessaid.AccessAid.config.documentation.Places.PlaceResponseExample;
import dev.accessaid.AccessAid.config.documentation.Ratings.RatingResponseExample;
import dev.accessaid.AccessAid.config.documentation.Ratings.TotalRatingResponseExample;
import dev.accessaid.AccessAid.config.documentation.Users.UserResponseExample;
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
        @GetMapping("")
        public Page<PlaceResponse> seeAllPlaces(Pageable pageable) {
                return placeMapper.toPlaceResponses(placeService.findAllPlaces(pageable), pageable);
        }

        @Operation(summary = "See a place by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Place found", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<PlaceResponse> seePlaceById(@PathVariable Integer id) {
                return ResponseEntity.ok(placeMapper.toPlaceResponse(placeService.findPlaceById(id)));
        }

        @Operation(summary = "See a place by address")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Place found", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/byaddress")
        public ResponseEntity<PlaceResponse> seePlaceByAddress(@RequestParam("address") String address) {
                return ResponseEntity.ok(placeMapper.toPlaceResponse(placeService.findPlaceByAddress(address)));
        }

        @Operation(summary = "See a place by coordinates")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Place found", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/bycoordinates")
        public ResponseEntity<PlaceResponse> seePlaceByCoordinates(@RequestParam("latitude") double latitude,
                        @RequestParam("longitude") double longitude) {
                return ResponseEntity.ok(placeMapper.toPlaceResponse(
                                placeService.findPlaceByCoordinates(latitude, longitude)));
        }

        @Operation(summary = "See a place by apiPlaceId")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Place found", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/byApiPlaceId")
        public ResponseEntity<PlaceResponse> seePlaceByApiPlaceId(@RequestParam("ApiPlaceId") String apiPlaceId) {
                return ResponseEntity.ok(placeMapper.toPlaceResponse(placeService.findPlaceByApiPlaceId(apiPlaceId)));
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
                return placeMapper.toPlaceResponse(placeService.createPlace(request));
        }

        @Operation(summary = "Delete a place by id")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Deleted successfully", content = @Content(schema = @Schema(implementation = PlaceResponseExample.class))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<?> deletePlace(@PathVariable Integer id) throws PlaceNotFoundException {
                return ResponseEntity.ok(placeMapper.toPlaceResponse(placeService.removePlace(id)));
        }

        @Operation(summary = "See all ratings for a place")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/{id}/ratings")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Page<RatingResponse>> seePlaceRatings(@PathVariable Integer id, Pageable pageable) {
                return ResponseEntity.ok(RatingMapper.toRatingResponses(
                                placeService.findAllRatingsByPlace(id, pageable), pageable));
        }

        @Operation(summary = "See users that have rated or commented a place")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseExample.class)))),
                        @ApiResponse(responseCode = "404", description = "Place not found", content = @Content)
        })
        @GetMapping("/{id}/users")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<?> seeUsersByPlace(@PathVariable Integer id, Pageable pageable) {
                return ResponseEntity.ok(UserMapper.toUserResponses(
                                placeService.findUsersByPlace(id, pageable), pageable));
        }

        @Operation(summary = "See the comments for a place")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CommentResponseExample.class)))),
                        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
        })
        @GetMapping("/{id}/comments")
        public Page<CommentResponse> seeCommentsByPlace(@PathVariable Integer id, Pageable pageable) {
                return CommentMapper.toCommentResponses(placeService.findCommentsByPlace(id, pageable), pageable);
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
        @GetMapping("/user/{userid}")
        public ResponseEntity<Page<PlaceResponse>> seePlacesByUser(@PathVariable Integer userid, Pageable pageable) {
                return ResponseEntity.ok(placeMapper.toPlaceResponses(
                                placeService.findPlacesByUser(userid, pageable), pageable));
        }

}
