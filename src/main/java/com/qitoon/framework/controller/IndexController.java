package com.qitoon.framework.controller;


import com.qitoon.framework.annotation.Get;
import com.qitoon.framework.param.Parameter;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
    @Ignore
    @Get(value = "/login", template = "/login")
    public Object login() {
        return "login";
    }

    @Ignore
    @Get(value = "/register", template = "/register")
    public Object register() {
        return "register";
    }


    @Get("/logout")
    public RedirectView logout(Parameter parameter) {
        parameter.getRequest().getSession().invalidate();
        return new RedirectView("/");
    }

    @Ignore
    @Get(value = "/about", template = "/about")
    public Object about() {
        return null;
    }
}
