package io.github.wonyoungpark.system.security;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by wyPark on 2016. 6. 29..
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if (account == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(account);
    }
}
