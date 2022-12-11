package dev.m8code.mettle.feature.repository;

import dev.m8code.mettle.feature.model.UserFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeatureRepository extends JpaRepository<UserFeature, Long> {
    UserFeature getUserFeatureByUserIdAndFeatureId(Long userId, Long featureId);
    List<UserFeature> getUserFeatureByUserId(Long userId);
}
