package com.quickgo.platform.controller;


import com.quickgo.platform.annotation.Get;
import com.quickgo.platform.param.Parameter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author huangjie
 * @since  2016-07-21
 */
@RestController
@RequestMapping("/")
public class IndexController {

    @Get(value = "/login", template = "/login")
    public Object login() {
        return "login";
    }


    @Get(value = "/register", template = "/register")
    public Object register() {
        return "register";
    }


    @Get("/logout")
    public RedirectView logout(Parameter parameter) {
        parameter.getRequest().getSession().invalidate();
        return new RedirectView("/");
    }

    @Get(value = "/about", template = "/about")
    public Object about() {
        return null;
    }
}
