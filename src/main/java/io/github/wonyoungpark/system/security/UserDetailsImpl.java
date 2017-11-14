package io.github.wonyoungpark.system.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.github.wonyoungpark.system.account.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wyPark on 2016. 6. 29..
 */
public class UserDetailsImpl extends User {
    @Getter
    @Setter
    private String name;

    public UserDetailsImpl(Account account) {
        super(account.getUsername(), account.getPassword(), getAuthorities(account));

        this.name = account.getName(); //principal의 name 속성 추가
    }

    // 권한 부여
    // 설명 : 권한을 부여한뒤 리턴해준다.
    private static Collection<? extends GrantedAuthority> getAuthorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(account.getRole()));

        return authorities;
    }
}
