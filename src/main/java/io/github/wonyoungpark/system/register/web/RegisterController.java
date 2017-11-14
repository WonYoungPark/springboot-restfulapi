package io.github.wonyoungpark.system.register.web;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.domain.AccountDto;
import io.github.wonyoungpark.system.account.service.AccountService;
import io.github.wonyoungpark.system.error.ErrorResponse;
import io.github.wonyoungpark.system.security.UserDetailsImpl;
import io.github.wonyoungpark.system.register.service.RegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    // 이동 : 회원가입 화면
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getPath() {
        return "common/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity register(@RequestBody @Valid AccountDto.Create create,
                                    BindingResult result // Validation 이전에 바인딩 결과를 확인할수 있다.
    )  throws URISyntaxException {
        if ( result.hasErrors() ) {
            // TODO 에러 응답 본문 추가하기
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            // BindingResult 안에 들어있는 에러정보 사용하기.
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // 회원정보를 저장
        Account newAccount = accountService.createAccount(create);

        // [STR] 회원가입 후 자동로그인 기능 구현
        // SecurityContextHolder에서 Context를 받아 인증 설정
        UserDetailsImpl userDetails = new UserDetailsImpl(newAccount);
        Authentication authentication =
                 new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // [END] 회원가입 후 자동로그인 기능 구현

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(new URI("redirect:/"));

        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class),
//                HttpStatus.CREATED);
    }
}
