package dev.m8code.mettle.feature.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTest {

    @Test
    void notUsedSetDeafault() {
        //given
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setGlobalEnabled(true);

        //then
        assertEquals(feature.getId(), 1L);
        assertTrue(feature.isGlobalEnabled());
    }
    @Test
    void setDefaultSettingsCase1() {
        //given
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setGlobalEnabled(true);
        //when
        feature.setDefaultSettings();
        //then
        assertNull(feature.getId());
        assertFalse(feature.isGlobalEnabled());
    }

    @Test
    void setDefaultSettingsCase2() {
        //given
        Feature feature = new Feature();
        feature.setGlobalEnabled(true);
        //when
        feature.setDefaultSettings();
        //then
        assertNull(feature.getId());
        assertFalse(feature.isGlobalEnabled());
    }
    @Test
    void setDefaultSettingsCase3() {
        //given
        Feature feature = new Feature();
        feature.setId(1L);
        //when
        feature.setDefaultSettings();
        //then
        assertNull(feature.getId());
        assertFalse(feature.isGlobalEnabled());
    }

    @Test
    void setDefaultSettingsCase4() {
        //given
        Feature feature = new Feature();
        //when
        feature.setDefaultSettings();
        //then
        assertNull(feature.getId());
        assertFalse(feature.isGlobalEnabled());
    }
}