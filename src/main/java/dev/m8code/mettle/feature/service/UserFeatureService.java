package dev.m8code.mettle.feature.service;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.model.UserFeature;
import dev.m8code.mettle.feature.repository.FeatureRepository;
import dev.m8code.mettle.feature.repository.UserFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFeatureService {
    private final UserFeatureRepository userFeatureRepository;
    private final FeatureRepository featureRepository;

    public Set<Feature> getAllUserFeature(Long userId) throws Exception {
        if (userId == null) throw new Exception("Bad request! UserId is null");
        List<UserFeature> userFeatures = userFeatureRepository.getUserFeatureByUserId(userId);
        List<Feature> globalEnabledFeatures = featureRepository.getFeaturesByGlobalEnabled(true);
        Set<Feature> allEnabledForUser = new HashSet<>();

        Set<Feature> featuresActiveForUserDirectly = userFeatures.stream()
                .map(UserFeature::getFeature)
                .collect(Collectors.toSet());
        allEnabledForUser.addAll(globalEnabledFeatures);
        allEnabledForUser.addAll(featuresActiveForUserDirectly);

        return allEnabledForUser;
    }
}
