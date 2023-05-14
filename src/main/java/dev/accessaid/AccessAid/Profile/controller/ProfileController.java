package dev.accessaid.AccessAid.Profile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.response.ProfileResponse;
import dev.accessaid.AccessAid.Profile.service.ProfileServiceImpl;
import dev.accessaid.AccessAid.Profile.utils.ProfileMapper;
import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.config.documentation.Profile.ProfileRequestExample;
import dev.accessaid.AccessAid.config.documentation.Profile.ProfileResponseExample;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profiles", description = "User Profiles")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileServiceImpl profileService;
    @Operation(summary = "See a list of profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProfileResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @Hidden
    @GetMapping("/unpaged")
    public List<ProfileResponse> seeAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return ProfileMapper.toProfileResponses(profiles);

    }

    @Operation(summary = "See a list of profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProfileResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public Page<ProfileResponse> seeAllProfiles(Pageable pageable) {
        Page<Profile> profiles = profileService.getAllProfiles(pageable);
        return ProfileMapper.toProfileResponses(profiles, pageable);

    }

    @Operation(summary = "See a profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProfileResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ProfileResponse seeProfileById(@PathVariable Integer id) {
        Profile profile = profileService.getProfileById(id);
        return ProfileMapper.toProfileResponse(profile);

    }

    @Operation(summary = "Add profile", description = "Create a profile for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully", content = @Content(schema = @Schema(implementation = ProfileResponseExample.class))),
            @ApiResponse(responseCode = "400", description = "Error saving profile", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse addProfile(
            @RequestBody @Validated @Schema(implementation = ProfileRequestExample.class) Profile profile) {
        Profile newProfile = profileService.createProfile(profile);
        return ProfileMapper.toProfileResponse(newProfile);

    }

    @Operation(summary = "Update an existing profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully", content = @Content(schema = @Schema(implementation = ProfileResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public ProfileResponse updateProfile(@PathVariable Integer id,
            @RequestBody @Validated @Schema(implementation = ProfileRequestExample.class) Profile profile) {
        profile.setId(id);
        Profile profileToUpdate = profileService.getProfileById(id);
        if (profileToUpdate == null) {
            throw new ProfileNotFoundException("Profile not found");
        }
        Profile updatedProfile = profileService.changeProfile(profile);
        return ProfileMapper.toProfileResponse(updatedProfile);

    }

    @Operation(summary = "Delete an existing profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable Integer id) {

        profileService.removeProfile(id);

    }

    @Operation(summary = "See profile by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = ProfileResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ProfileResponse seeProfileByUser(@PathVariable Integer userId) {
        Profile profile = profileService.getProfileByUser(userId);
        return ProfileMapper.toProfileResponse(profile);

    }
}
