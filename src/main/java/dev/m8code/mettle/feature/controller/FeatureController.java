package dev.m8code.mettle.feature.controller;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeatureController {
    private final FeatureService featureService;

    @PostMapping(path = "/features/add", consumes ="application/json")
    public void addNewFeature(@RequestBody Feature feature) {
        featureService.addFeature(feature);
    }

    @GetMapping(path = "/features", produces = "application/json")
    @ResponseBody
    public List<Feature> getAllFeatures() {
        return featureService.getAllFeatures();

    }
}
