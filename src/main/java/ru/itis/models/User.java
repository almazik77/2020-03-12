package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String hashPassword;
    private String email;
    private String confirmString;

    private Role role;

    private State state;
}
