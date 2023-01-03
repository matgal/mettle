package dev.m8code.mettle.feature.service;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.model.UserFeature;
import dev.m8code.mettle.feature.repository.FeatureRepository;
import dev.m8code.mettle.feature.repository.UserFeatureRepository;
import dev.m8code.mettle.user.model.User;
import dev.m8code.mettle.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FeatureFlagServiceTest {

    private final UserRepository userRepository;
    private final UserFeatureRepository userFeatureRepository;
    private final FeatureRepository featureRepository;

    private final FeatureFlagService featureFlagService;

    public FeatureFlagServiceTest() {
        userRepository = Mockito.mock(UserRepository.class);
        userFeatureRepository = Mockito.mock(UserFeatureRepository.class);
        featureRepository = Mockito.mock(FeatureRepository.class);
        featureFlagService = new FeatureFlagService(userRepository, userFeatureRepository, featureRepository);
    }

    @Test
    @DisplayName("New feature save to DB for correct user and feature")
    void newFeatureSaveToDbForCorrectUserAndFeature() throws Exception {
        //given
        Long featureId = 1L;
        Long userId = 1L;
        boolean isEnabled = true;

        returnFeature(featureId);
        returnUser(userId);
        returnNullUserFeature(featureId, userId);

        //when
        featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled);

        //then
        verify(userRepository).findById(userId);
        verify(featureRepository).findById(featureId);
        verify(userFeatureRepository).save(any(UserFeature.class));
    }

    @Test
    @DisplayName("Feature deleted from DB for correct user and feature")
    void featureDeletedFromDbForCorrectUserAndFeature() throws Exception {
        //given
        Long featureId = 1L;
        Long userId = 1L;
        boolean isEnabled = false;

        returnFeature(featureId);
        returnUser(userId);
        returnUserFeature(featureId, userId);

        //when
        featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled);

        //then
        verify(userRepository).findById(userId);
        verify(featureRepository).findById(featureId);
        verify(userFeatureRepository).delete(any(UserFeature.class));
    }

    @Test
    @DisplayName("Throw exception Bad Request when user data not correct")
    void throwExceptionBadRequestWhenUserDataNotCorrect() {
        //given
        Long featureId = 1L;
        Long userId = null;
        boolean isEnabled = false;

        returnFeature(featureId);
        returnUser(userId);
        returnUserFeature(featureId, userId);

        //when
        //then
        Exception exception = Assertions.assertThrows(Exception.class, () -> featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled));
        Assertions.assertEquals(exception.getMessage(), "Bad request! UserId is null.");
    }

    @Test
    @DisplayName("Throw exception Bad Request when feature data not correct")
    void throwExceptionBadRequestWhenFeatureDataNotCorrect() {
        //given
        Long featureId = null;
        Long userId = 1L;
        boolean isEnabled = false;

        returnFeature(featureId);
        returnUser(userId);
        returnUserFeature(featureId, userId);

        //when
        //then
        Exception exception = Assertions.assertThrows(Exception.class, () -> featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled));
        Assertions.assertEquals(exception.getMessage(), "Bad request! FeatureId is null.");
    }

    @Test
    @DisplayName("Throw exception Bad Request when user not exist")
    void throwExceptionBadRequestWhenUserNotExist() {
        //given
        Long featureId = 1L;
        Long userId = 1L;
        boolean isEnabled = false;

        returnFeature(featureId);
        returnNullUser(userId);
        returnUserFeature(featureId, userId);

        //when
        //then
        Exception exception = Assertions.assertThrows(Exception.class, () -> featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled));
        Assertions.assertEquals(exception.getMessage(), "User not found");
    }

    @Test
    @DisplayName("Throw exception Bad Request when feature not exist")
    void throwExceptionBadRequestWhenFeatureNotExist() {
        //given
        Long featureId = 1L;
        Long userId = 1L;
        boolean isEnabled = false;

        returnNullFeature(featureId);
        returnUser(userId);
        returnNullUserFeature(featureId, userId);

        //when
        //then
        Exception exception = Assertions.assertThrows(Exception.class, () -> featureFlagService.changeFeatureFlagForUser(featureId, userId, isEnabled));
        Assertions.assertEquals(exception.getMessage(), "Feature not found");
    }


    private OngoingStubbing<UserFeature> returnUserFeature(Long featureId, Long userId) {
        return when(userFeatureRepository.getUserFeatureByUserIdAndFeatureId(userId, featureId)).thenReturn(new UserFeature());
    }

    private OngoingStubbing<Optional<User>> returnUser(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setName("TEST");
        return when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    private OngoingStubbing<Optional<Feature>> returnFeature(Long featureId) {
        return when(featureRepository.findById(featureId)).thenReturn(Optional.of(new Feature()));
    }

    private OngoingStubbing<UserFeature> returnNullUserFeature(Long featureId, Long userId) {
        return when(userFeatureRepository.getUserFeatureByUserIdAndFeatureId(userId, featureId)).thenReturn(null);
    }

    private OngoingStubbing<Optional<User>> returnNullUser(Long userId) {
        return when(userRepository.findById(userId)).thenReturn(Optional.empty());
    }

    private OngoingStubbing<Optional<Feature>> returnNullFeature(Long featureId) {
        return when(featureRepository.findById(featureId)).thenReturn(Optional.empty());
    }
}