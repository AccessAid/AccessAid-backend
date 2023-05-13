package dev.accessaid.AccessAid.Accessibility.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Accessibility.service.AccessibilityService;
import dev.accessaid.AccessAid.config.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Accessibility", description = "Place details")
@RestController
public class AccessibilityController {

    @Autowired
    private AccessibilityService accessibilityService;

    @GetMapping("/api/accessibility/{placeId}")
    public ResponseEntity<?> getPlaceDetails(@PathVariable String placeId) {
        try {
            AccessibilityResponse response = accessibilityService.getPlaceDetails(placeId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
