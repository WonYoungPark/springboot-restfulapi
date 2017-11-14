package io.github.wonyoungpark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by wyPark on 2016. 6. 29..
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    // 설명 : 특정 요청에 대해서 시큐리티 설정을 무시하도록 하는 정체에 관한 설정을 함.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/font-awesome/**", "/fonts/**", "/img/**", "/js/**", "/webjars/**", "/h2console/**");
    }

    // 설명 : userDetailsService와 passwordEncoder를 AuthenticationManagerBuilder에 세팅해 준다.(사용자의 username과 password가 맞는지 확인한다.)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    // Http 설정
    // 설명 : 인가, 로그인, 로그아웃을 설정한다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 인가 관련 설정
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/accounts/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/accounts/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("ADMIN")
                .antMatchers("/login").permitAll() // /login 경로에 모든 사용자 접속을 허용함.
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated() // 그외의 경로는 인증없이 접속을 허용하지 않음.
        ;

        // 로그인 관련 설정
        http
            .formLogin() // 인증 처리 유효화
                .loginPage("/login") // 로그인 페이지 경로
                .loginProcessingUrl("/login") // 로그인 Form 표시 경로(HTML form tag의 action 값과 동일해야함.) : 로그인 처리 경로 + 로그인 form 표시 경로
                .failureUrl("/login?error") // 인증 실패시 이동 경로
                .defaultSuccessUrl("/", true) // 인증 성공시 이동 경로
                .usernameParameter("username").passwordParameter("password") // 사용자 이름과 암호 관련 파라미터 이름 설정
        ;

        // 로그아웃 관련 설정
        http
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // 로그아웃 처리 경로 // AntPathRequestMatcher 클래스를 사용하지 않고 문자열 경로를 지정한 상황에서 로그아웃 하려면 POST로 접속해야 함.
            .logoutSuccessUrl("/login?error") // 로그아웃 성공시 이동 경로
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
