package dev.m8code.mettle.feature.model;

import dev.m8code.mettle.user.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class UserFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Feature feature;

    private UserFeature(User user, Feature feature) {
        this.user = user;
        this.feature = feature;
    }

    public static UserFeature create(User user, Feature feature) {
        return new UserFeature(user, feature);
    }
}
