package io.github.wonyoungpark.system.login.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by wyPark on 2016. 6. 28..
 */
@Data
public class LoginDto {

    @Data
    public static class Request {
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
        private String name;
    }
}
