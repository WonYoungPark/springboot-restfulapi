package io.github.wonyoungpark.system.login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.github.wonyoungpark.system.login.service.LoginService;

/**
 * Created by wyPark on 2016. 6. 18..
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService service;

    @RequestMapping(value = "/admin")
    public void admin() {
    }

    // 이동 : 로그인 화면
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getPath() {
        return "common/login";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpServletRequest request) {
//
//        // TODO : 세션설정
//        setSession(request.getSession(), "");
//        return "";
//    }
//
//    // 세션 설정
//    private void setSession(HttpSession hss, String strUserId)
//    {
//        hss.setAttribute(Base.LOGIN_USER_ID, strUserId);
//    }
}
