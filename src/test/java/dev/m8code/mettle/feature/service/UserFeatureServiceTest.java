package dev.m8code.mettle.feature.service;

import dev.m8code.mettle.feature.model.Feature;
import dev.m8code.mettle.feature.model.UserFeature;
import dev.m8code.mettle.feature.repository.FeatureRepository;
import dev.m8code.mettle.feature.repository.UserFeatureRepository;
import dev.m8code.mettle.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserFeatureServiceTest {
    private final UserFeatureRepository userFeatureRepository;
    private final FeatureRepository featureRepository;

    private final UserFeatureService userFeatureService;

    public UserFeatureServiceTest() {
        userFeatureRepository = Mockito.mock(UserFeatureRepository.class);
        featureRepository = Mockito.mock(FeatureRepository.class);
        userFeatureService = new UserFeatureService(userFeatureRepository, featureRepository);
    }

    @Test
    @DisplayName("Two global features and one user feature, all unique")
    void twoGlobalFeaturesAndOneUserFeatureAllUnique() throws Exception {
        //given
        Long userId = 1L;
        returnTwoGlobalFeature();
        returnOneUserFeature(userId);
        //when
        Set<Feature> allUserFeature = userFeatureService.getAllUserFeature(userId);
        //then
        assertEquals(allUserFeature.size(), 3);
    }
    @Test
    @DisplayName("Two global features and two user feature, all unique")
    void twoGlobalFeaturesAnd1UserFeatureAllUnique() throws Exception {
        //given
        Long userId = 1L;
        returnTwoGlobalFeature();
        returnTwoUserFeature(userId);
        //when
        Set<Feature> allUserFeature = userFeatureService.getAllUserFeature(userId);
        //then
        assertEquals(allUserFeature.size(), 4);
    }
    @Test
    @DisplayName("Two global features and two user feature, one overlapping")
    void twoGlobalFeaturesAndTwoUserFeatureOneOverlapping() throws Exception {
        //given
        Long userId = 1L;
        returnTwoGlobalFeatureAndTwoUserFeatureOneOverlapped();
        //when
        Set<Feature> allUserFeature = userFeatureService.getAllUserFeature(userId);
        //then
        assertEquals(allUserFeature.size(), 3);
    }

    private OngoingStubbing<List<UserFeature>> returnOneUserFeature(Long userId) {
        UserFeature e1 = new UserFeature();
        Feature feature = new Feature();
        feature.setId(1L);
        e1.setFeature(feature);
        return when(userFeatureRepository.getUserFeatureByUserId(userId)).thenReturn(List.of(e1));
    }

    private OngoingStubbing<List<Feature>> returnTwoGlobalFeature() {
        Feature e1 = new Feature();
        e1.setId(21L);
        Feature e2 = new Feature();
        e2.setId(22L);
        return when(featureRepository.getFeaturesByGlobalEnabled(true)).thenReturn(List.of(e1, e2));
    }

    private OngoingStubbing<List<UserFeature>> returnTwoUserFeature(Long userId) {
        UserFeature e1 = new UserFeature();
        Feature feature = new Feature();
        feature.setId(1L);
        e1.setFeature(feature);

        UserFeature e2 = new UserFeature();
        Feature feature2 = new Feature();
        feature.setId(2L);
        e2.setFeature(feature2);
        return when(userFeatureRepository.getUserFeatureByUserId(userId)).thenReturn(List.of(e1, e2));
    }

    private void returnTwoGlobalFeatureAndTwoUserFeatureOneOverlapped() {
        Feature e1 = new Feature();
        e1.setId(21L);
        Feature e2 = new Feature();
        e2.setId(22L);
        when(featureRepository.getFeaturesByGlobalEnabled(true)).thenReturn(List.of(e1, e2));

        UserFeature e3 = new UserFeature();
        Feature feature = new Feature();
        feature.setId(1L);
        e3.setFeature(feature);

        UserFeature e4 = new UserFeature();
        Feature feature2 = new Feature();
        feature.setId(22L);
        e4.setFeature(feature2);
        when(userFeatureRepository.getUserFeatureByUserId(any())).thenReturn(List.of(e3, e4));
    }
}