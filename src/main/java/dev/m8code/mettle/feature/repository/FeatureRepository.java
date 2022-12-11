package dev.m8code.mettle.feature.repository;

import dev.m8code.mettle.feature.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> getFeaturesByGlobalEnabled(boolean globalEnabled);
}
