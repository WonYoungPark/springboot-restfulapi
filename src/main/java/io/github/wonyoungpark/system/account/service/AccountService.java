package io.github.wonyoungpark.system.account.service;

import java.util.Date;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.domain.AccountDto;
import io.github.wonyoungpark.system.account.domain.AccountDuplicatedException;
import io.github.wonyoungpark.system.account.domain.AccountNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wonyoungpark.system.account.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@Service
@Transactional
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public Account createAccount(AccountDto.Create dto) {

        //dto 객체 안에 들어있는 내용을 Account 클래스타입의 인스턴스로 옮긴다.
        Account account = modelMapper.map(dto, Account.class);
        // 동일코드 modelMapper 를 통해 한줄로 요약가능
//        Account account = new Account();
//        account.setUserId(dto.getUserId());
//        account.setUserName(dto.getUserName());
//        account.setPassword(dto.getPassword());
//        account.setRgstUserId(dto.getUserId());
//        account.setUpdtUserId(dto.getUserId());

        String username = dto.getUsername();
        if (repository.findByUsername(username) != null) {
            log.error("user duplicated excpetion. {}", username);
            throw new AccountDuplicatedException(username);
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        Date now = new Date();
        account.setRgstDt(now);
        account.setUpdtDt(now);

        return repository.save(account);
    }

    public Account getAccount(Long id) {
        Account account = repository.findOne(id);

        if (account == null) {
            throw new AccountNotFoundException(id);
        }

        return account;
    }

    public Account getAccount(String username) {
        Account account = repository.findByUsername(username);

        if (null == account) {
            //throw new AccountNotFoundException()
        }

        return account;
    }

    public Account updateAccount(Long id, AccountDto.Update updateDto) {
        Account account = getAccount(id);
        //account.setPassword(passwordEncoder.encode(account.getPassword()));
        return repository.save(account);
    }

    public void deleteAccount(Long id) {
        repository.delete(getAccount(id));
    }
}