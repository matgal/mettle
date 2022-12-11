package dev.m8code.mettle.feature.service;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public void addFeature(Feature feature) {
        feature.setDefaultSettings();
        featureRepository.save(feature);
    }

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }
}
