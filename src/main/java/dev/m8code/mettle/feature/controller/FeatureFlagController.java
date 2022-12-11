package dev.m8code.mettle.feature.controller;

import dev.m8code.mettle.feature.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;

    @GetMapping(path = "/users/{userId}/features/{featureId}/{isEnable}")
    public ResponseEntity<Void> switchFeatureForUser(@PathVariable("featureId") Long featureId, @PathVariable("userId") Long userId, @PathVariable("isEnable") boolean isEnable) {
        try {
            featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnable);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
