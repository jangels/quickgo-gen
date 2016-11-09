package com.quickgo.platform.controller;

import com.quickgo.platform.model.User;
import com.quickgo.platform.utils.MemoryUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
@RestController
@RequestMapping("/genCode")
public class GenApiCodeController {

    @RequestMapping("/")
    public Object generateCode(String token) {
        User user = MemoryUtils.getUser(token);


        return null;

    }
}
