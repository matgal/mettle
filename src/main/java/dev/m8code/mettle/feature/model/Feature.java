package dev.m8code.mettle.feature.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "global_enabled")
    private boolean globalEnabled;

    public void setDefaultSettings() {
        id = null;
        globalEnabled = false;
    }
}
