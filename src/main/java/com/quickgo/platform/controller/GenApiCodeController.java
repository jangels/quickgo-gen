package com.quickgo.platform.controller;

import com.quickgo.platform.face.IGenApiCodeService;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.utils.MemoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Object generateCode(String token,List<String> stringList ) {
        User user = MemoryUtils.getUser(token);
        if(user==null){
            return Result.fail("无权限操作");
        }
//        List<String> stringList = new ArrayList<>();
//        stringList.add("3YfaaoWha");
//        stringList.add("3SBdpkoIZ");
        if(CollectionUtils.isEmpty(stringList)){
            return Result.fail("数据为空");
        }
       String str = genApiCodeService.findList(stringList);
        return Result.returnInfo(str);

    }
}
