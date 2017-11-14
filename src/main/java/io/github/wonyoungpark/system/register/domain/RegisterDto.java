package io.github.wonyoungpark.system.register.domain;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

public class RegisterDto {
    @Data
    public static class Create {
        @NotBlank
        @Size(min = 5, max = 20)
        private String username;

        @NotBlank
        @Size(min = 5, max = 20)
        private String password;
    }

    @Data
    public static class Response {
        private Long id;
        private String username;
    }

    @Data
    public static class Update {
        private String password;
        private Date rgstDt;
        private Long rgstId;
        private Date updtDt;
        private Long updtId;
    }
}
