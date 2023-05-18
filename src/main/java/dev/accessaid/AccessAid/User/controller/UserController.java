package dev.accessaid.AccessAid.User.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.response.UserResponse;
import dev.accessaid.AccessAid.User.service.UserServiceImpl;
import dev.accessaid.AccessAid.User.utils.UserMapper;
import dev.accessaid.AccessAid.config.ErrorResponse;
import dev.accessaid.AccessAid.config.documentation.Users.UserRequestExample;
import dev.accessaid.AccessAid.config.documentation.Users.UserResponseExample;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users", description = "Information about Users")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Operation(summary = "See a list of users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public Page<UserResponse> seeAllUsers(Pageable pageable) {
        return UserMapper.toUserResponses(userService.getUsers(pageable), pageable);
    }

    @Operation(summary = "See a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{id}")
    public UserResponse seeUserById(@PathVariable Integer id) {
        return UserMapper.toUserResponse(userService.getUserById(id));
    }

    @Hidden
    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            UserResponse response = UserMapper.toUserResponse(newUser);
            return ResponseEntity.ok(response);
        } catch (UserSaveException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @Operation(summary = "Update an existing profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully", content = @Content(schema = @Schema(implementation = UserResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public UserResponse updateUser(@RequestBody @Validated @Schema(implementation = UserRequestExample.class) User user,
            @PathVariable Integer id) {
        user.setId(id);
        User userToUpdate = userService.getUserById(id);
        if (userToUpdate == null) {
            throw new UserNotFoundException("User not found");
        }
        userToUpdate.updateFields(user);
        User updatedUser = userService.changeUser(userToUpdate);
        return UserMapper.toUserResponse(updatedUser);

    }

    @Operation(summary = "Delete an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.removeUser(id);

    }

    @Operation(summary = "See user by profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/profile/{profileId}")
    public UserResponse seeUserByProfile(@PathVariable Integer profileId) {
        User user = userService.getUserByProfile(profileId);
        return UserMapper.toUserResponse(user);

    }

    @Operation(summary = "See user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/email/{email}")
    public UserResponse seeUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return UserMapper.toUserResponse(user);

    }

    @Operation(summary = "See user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/username/{username}")
    public UserResponse seeUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return UserMapper.toUserResponse(user);

    }

}