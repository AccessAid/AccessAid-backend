package dev.accessaid.AccessAid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.service.RequestPlacesAPI;

@RestController
public class AccessibilityController {

    @Autowired
    private RequestPlacesAPI requestPlacesAPI;

    @GetMapping("/accessibility")
    public ResponseEntity<String> getAccessibilityInfo(@RequestParam double latitude, @RequestParam double longitude) {
        String accessibilityInfo = requestPlacesAPI.getAccesibilityInfo(latitude, longitude);
        return ResponseEntity.ok(accessibilityInfo);
    }
}