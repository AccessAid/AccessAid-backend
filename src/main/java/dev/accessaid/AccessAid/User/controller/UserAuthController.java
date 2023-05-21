package dev.accessaid.AccessAid.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.config.documentation.Users.LoginRequestExample;
import dev.accessaid.AccessAid.config.documentation.Users.MessageResponseAUTHExample;
import dev.accessaid.AccessAid.config.documentation.Users.UserRequestExample;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import dev.accessaid.AccessAid.security.payload.TokenRefreshRequest;
import dev.accessaid.AccessAid.security.payload.TokenRefreshResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

        @Autowired
        private UserService userService;

        public UserAuthController(UserService userService) {
                this.userService = userService;
        }

        @Operation(summary = "User registration", description = "Register a new user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "user was registered correctly", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseAUTHExample.class))
                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
        })
        @PostMapping("/register")
        public ResponseEntity<MessageResponse> registerUser(
                        @Valid @RequestBody @Schema(implementation = UserRequestExample.class) RegisterRequest signUpRequest) {
                return userService.registerUser(signUpRequest);
        }

        @Operation(summary = "User login", description = "User authentication")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Ok", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
                        }),
                        @ApiResponse(responseCode = "401", description = "unauthorized"),
        })
        @PostMapping("/login")
        public ResponseEntity<MessageResponse> loginUser(
                        @RequestBody @Validated @Schema(implementation = LoginRequestExample.class) LoginRequest loginRequest) {
                return userService.loginUser((loginRequest));
        }

        @Operation(summary = "Refresh token", description = "Refresh token")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Ok", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TokenRefreshResponse.class))
                        }),
                        @ApiResponse(responseCode = "401", description = "unauthorized"),
        })
        @PostMapping("/refreshtoken")
        public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
                return userService.refreshtoken(request);
        }
}
