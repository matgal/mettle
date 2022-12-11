package dev.m8code.mettle.feature.service;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.model.UserFeature;
import dev.m8code.mettle.feature.repository.FeatureRepository;
import dev.m8code.mettle.feature.repository.UserFeatureRepository;
import dev.m8code.mettle.user.model.User;
import dev.m8code.mettle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagService {
    private final UserRepository userRepository;
    private final UserFeatureRepository userFeatureRepository;
    private final FeatureRepository featureRepository;


    public void changeFeatureFlagForUser(Long featureId, Long userId, boolean isEnable) throws Exception {
        Feature feature = featureRepository.getReferenceById(featureId);
        User user = userRepository.getReferenceById(userId);
        UserFeature userFeature = userFeatureRepository.getUserFeatureByUserIdAndFeatureId(userId, featureId);
        changeUserFeature(isEnable, feature, user, userFeature);
    }

    private void changeUserFeature(boolean isEnable, Feature feature, User user, UserFeature userFeature) throws Exception {
        if (userFeature == null && isEnable) {
            UserFeature newUserFeature = UserFeature.create(user, feature);
            userFeatureRepository.save(newUserFeature);
            log.info("New userFeature saved [{}]", newUserFeature);
            return;
        }

        if (userFeature != null && isEnable) {
            throw new Exception("Bad request!");
        }

        if (userFeature != null && !isEnable) {
            userFeatureRepository.delete(userFeature);
        }
    }
}
