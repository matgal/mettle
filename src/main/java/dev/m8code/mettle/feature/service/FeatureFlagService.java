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
        validateEntryData(featureId, userId);
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new Exception("Feature not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
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
            throw new Exception("Bad request! Feature is active for user");
        }

        if (userFeature != null) {
            userFeatureRepository.delete(userFeature);
            log.info("Feature [{}] deactivated for user [{}]", userFeature, user);
        }
    }

    private void validateEntryData(Long featureId, Long userId) throws Exception {
        if (featureId == null) {
            throw new Exception("Bad request! FeatureId is null.");
        }
        if (userId == null) {
            throw new Exception("Bad request! UserId is null.");
        }
    }
}
