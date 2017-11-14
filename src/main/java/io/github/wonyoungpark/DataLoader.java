package io.github.wonyoungpark;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by wyPark on 2016. 7. 7..
 */
// 테스트용 데이터 입력하는데 사용한다.
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // [S] 고객 정보 등록
        // 관리자
        Account account1 = new Account();
        account1.setUsername("admin@gmail.com");
        account1.setPassword(passwordEncoder.encode("1234"));
        account1.setRole("ROLE_ADMIN");

        accountRepository.save(account1);

        // 유저
        Account account2 = new Account();
        account2.setUsername("user@gmail.com");
        account2.setPassword(passwordEncoder.encode("1234"));
        account2.setRole("ROLE_USER");

        accountRepository.save(account2);
        // [E] 고객 정보 등록
    }
}
