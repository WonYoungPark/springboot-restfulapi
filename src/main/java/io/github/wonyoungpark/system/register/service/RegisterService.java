package io.github.wonyoungpark.system.register.service;

import io.github.wonyoungpark.system.account.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wonyoungpark.system.register.repository.RegisterRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RegisterService {
    @Autowired
    private RegisterRepository registerRepository;

    public Account register(Account account) {

        registerRepository.save(account);
        return account;
    }
}
