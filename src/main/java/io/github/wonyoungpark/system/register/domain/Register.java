package io.github.wonyoungpark.system.register.domain;

import lombok.Data;

@Data
public class Register {
    private String username;

    private String password;

    private String name;

    private String role;
}
