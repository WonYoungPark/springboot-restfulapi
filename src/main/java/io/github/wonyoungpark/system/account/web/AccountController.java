package io.github.wonyoungpark.system.account.web;

import io.github.wonyoungpark.system.account.domain.Account;
import io.github.wonyoungpark.system.account.domain.AccountDto;
import io.github.wonyoungpark.system.account.domain.AccountDuplicatedException;
import io.github.wonyoungpark.system.account.domain.AccountNotFoundException;
import io.github.wonyoungpark.system.account.repository.AccountRepository;
import io.github.wonyoungpark.system.account.service.AccountService;
import io.github.wonyoungpark.system.error.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * Created by wyPark on 2016. 6. 13..
 */
@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/accountGrid", method = RequestMethod.GET)
    public String getPath() {
        return "account/accountGrid";
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
                                        BindingResult result//, // Validation 이전에 바인딩 결과를 확인할수 있다.
                                        //@AuthenticationPrincipal UserDetailsImpl userDetailsImpl // 로그인 상태에 있는 UserDetailsImpl 객체를 가져올수 있다.
    ) {
        if ( result.hasErrors() ) {
            // TODO 에러 응답 본문 추가하기
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            // BindingResult 안에 들어있는 에러정보 사용하기.
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(create);
        //Account newAccount = service.createAccount(create, userDetailsImpl.getUsername); // 계정 추가를 할시 로그인 상태 계정명을 입력할때

        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class),
                HttpStatus.CREATED);
    }

    @RequestMapping(value = "/accounts", method = GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PageImpl<AccountDto.Response> getAccounts(Pageable pageable) {
        Page<Account> page = repository.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @RequestMapping(value = "/accounts/{id}", method = GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public AccountDto.Response getAccount(@PathVariable Long id) {
        Account account = service.getAccount(id);

        return modelMapper.map(account, AccountDto.Response.class);
    }

    @RequestMapping(value = "/accounts/{id}", method = PUT)
    public ResponseEntity updateAccount(@PathVariable Long id,
                                        @RequestBody @Valid AccountDto.Update updateDto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account updateAccount = service.updateAccount(id, updateDto);

        return new ResponseEntity<>(modelMapper.map(updateAccount, AccountDto.Response.class), HttpStatus.OK);
    }

    @RequestMapping(value = "accounts/{id}", method = DELETE)
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        service.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 예외처리
    @ExceptionHandler(AccountDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountDuplicatedException(AccountDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getUsername() + "] 중복된 username 입니다.");
        errorResponse.setCode("duplicated.userId.exception");
        return errorResponse;
    }

    // 예외처리
    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "]에 해당하는 계정이 없습니다.");
        errorResponse.setCode("account.not.found.exception");
        return errorResponse;
    }
}

