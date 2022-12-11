package dev.m8code.mettle.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "client_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
