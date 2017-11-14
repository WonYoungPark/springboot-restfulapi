package io.github.wonyoungpark.system.login.service;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.service.AccountService;
import io.github.wonyoungpark.system.login.domain.Login;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wyPark on 2016. 6. 28..
 */
@Service
public class LoginService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    public Login login(Login login) {

        Account account = new Account();

        account = accountService.getAccount(login.getUsername());

        if (account.getId() == null) // 사용자 존재하지 않음.
        {

        } else
        if (account.getPassword() != login.getPassword()) // 비밀번호 다름
        {

        } else
        {
            // TODO : 로그인 로그 등록
        }

        return login;
    }
}
