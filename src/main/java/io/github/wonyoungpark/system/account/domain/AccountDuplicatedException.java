package io.github.wonyoungpark.system.account.domain;

/**
 * Created by wyPark on 2016. 6. 13..
 */
public class AccountDuplicatedException extends RuntimeException {

    private String username;

    public AccountDuplicatedException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
