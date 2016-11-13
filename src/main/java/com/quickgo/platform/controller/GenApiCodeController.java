package com.quickgo.platform.controller;

import com.quickgo.platform.face.IGenApiCodeService;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.utils.MemoryUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
@RestController
@RequestMapping("/code")
public class GenApiCodeController {

    @Autowired
    private IGenApiCodeService genApiCodeService;

    @RequestMapping("/generateCode")
    public Object generateCode(String token, String id) {
        User user = MemoryUtils.getUser(token);
        if (user == null) {
            return Result.fail("无权限操作");
        }
        if (StringUtils.isEmpty(id)) {
            return Result.fail("参数id为空");
        }
        String[] ids = id.split(",");
        List<String> stringList = Arrays.asList(ids);
        String str = genApiCodeService.findList(stringList);
        return Result.returnInfo(str);

    }
}
