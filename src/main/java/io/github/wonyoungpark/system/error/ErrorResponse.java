package io.github.wonyoungpark.system.error;

import lombok.Data;

import java.util.List;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@Data
public class ErrorResponse {

    private String message;

    private String code;

    private List<FieldError> errors;

    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }
}