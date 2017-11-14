package io.github.wonyoungpark.system.register.repository;

import io.github.wonyoungpark.system.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Account, Long> {

}
