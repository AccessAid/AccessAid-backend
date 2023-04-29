package dev.accessaid.AccessAid.Profile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.response.ProfileResponse;
import dev.accessaid.AccessAid.Profile.service.ProfileServiceImpl;
import dev.accessaid.AccessAid.Profile.utils.ProfileMapper;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.config.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Profiles", description = "User Profiles")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileServiceImpl profileService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> seeAllProfiles() {
        try {
            List<Profile> profiles = profileService.getAllProfiles();
            List<ProfileResponse> responses = ProfileMapper.toProfileResponses(profiles);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> seeProfileById(@PathVariable Integer id) {
        try {
            Profile profile = profileService.getProfileById(id);
            ProfileResponse response = ProfileMapper.toProfileResponse(profile);
            return ResponseEntity.ok(response);
        } catch (ProfileNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PostMapping("")
    public ResponseEntity<?> addProfile(@RequestBody Profile profile) {
        try {
            Profile newProfile = profileService.createProfile(profile);
            ProfileResponse response = ProfileMapper.toProfileResponse(newProfile);
            return ResponseEntity.ok(response);
        } catch (ProfileSaveException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @PathVariable Integer id) {
        try {
            profile.setId(id);
            Profile profileToUpdate = profileService.getProfileById(id);
            if (profileToUpdate == null) {
                ErrorResponse errorResponse = new ErrorResponse("Rating not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            Profile updatedProfile = profileService.changeProfile(profile);
            ProfileResponse response = ProfileMapper.toProfileResponse(updatedProfile);
            return ResponseEntity.ok(response);
        } catch (ProfileNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Integer id) {
        try {
            Profile profileToDelete = profileService.removeProfile(id);

            ProfileResponse response = ProfileMapper.toProfileResponse(profileToDelete);
            return ResponseEntity.ok(response);
        } catch (ProfileNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> seeProfileByUser(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                ErrorResponse errorResponse = new ErrorResponse("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            Profile profile = profileService.getProfileByUser(user);
            if (profile == null) {
                ErrorResponse errorResponse = new ErrorResponse("Profile not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            ProfileResponse response = ProfileMapper.toProfileResponse(profile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
