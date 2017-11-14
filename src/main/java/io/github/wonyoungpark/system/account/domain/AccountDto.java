package io.github.wonyoungpark.system.account.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by wyPark on 2016. 6. 13..
 */
public class AccountDto {
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
        private Date rgstDt;
        private Date updtDt;
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