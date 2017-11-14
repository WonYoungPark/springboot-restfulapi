package io.github.wonyoungpark.system.account.domain;

/**
 * Created by wyPark on 2016. 6. 13..
 */
public class AccountNotFoundException extends RuntimeException {

    private Long id;

    public AccountNotFoundException(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

