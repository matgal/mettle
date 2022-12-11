package dev.m8code.mettle.feature.controller;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.model.UserFeature;
import dev.m8code.mettle.feature.service.UserFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class UserFeatureController {
    private final UserFeatureService userFeatureService;


    @GetMapping(path = "/users/{userId}/features")
    public ResponseEntity<Set<Feature>> userFeatures(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(userFeatureService.getAllUserFeature(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
