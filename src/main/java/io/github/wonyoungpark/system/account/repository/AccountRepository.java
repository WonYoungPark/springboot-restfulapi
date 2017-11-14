package io.github.wonyoungpark.system.account.repository;

import io.github.wonyoungpark.system.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}



