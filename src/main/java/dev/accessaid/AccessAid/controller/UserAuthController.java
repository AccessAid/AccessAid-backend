package dev.accessaid.AccessAid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
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

        @Operation(summary = "Registro de usuarios", description = "Registrar un nuevo usuario")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "user was registered correctly", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))
                        }),
                        @ApiResponse(responseCode = "400", description = "Bad Request"),
        })
        @PostMapping("/register")
        public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
                return userService.registerUser(signUpRequest);
        }

        @Operation(summary = "Autenticacion de usuarios", description = "Autenticar un usuario")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Ok", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))
                        }),
                        @ApiResponse(responseCode = "401", description = "unauthorized"),
        })
        @PostMapping("/login")
        public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest loginRequest) {
                return userService.loginUser((loginRequest));
        }
}
