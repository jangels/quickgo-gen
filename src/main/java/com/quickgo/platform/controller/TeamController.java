package com.quickgo.platform.controller;


import com.quickgo.platform.model.Team;
import com.quickgo.platform.model.User;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.utils.AssertUtils;
import com.quickgo.platform.utils.BeanCopy;
import com.quickgo.platform.utils.MemoryUtils;
import com.quickgo.platform.utils.Validate;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 团队
 * //todo 权限验证
 * @author huangjie
 * @since  2016-07-20
 */
@Deprecated
@RestController
@RequestMapping("/team")
public class TeamController {

    public Object create(Parameter parameter){
        User user = MemoryUtils.getUser(parameter);
        Team team = BeanCopy.convert(Team.class,parameter.getParamString());
        AssertUtils.notNull(team.getName(),"missing name");
        team.setId(Validate.id());
        team.setCreateTime(new Date().getTime());
        team.setUserId(user.getId());
        team.setStatus(Team.Status.VALID);
        int rs =0;// ServiceFactory.instance().create(team);
        AssertUtils.isTrue(rs > 0,"操作失败");
        return team.getId();
    }


    public Object update(@RequestParam("id") String id, Parameter parameter){
        Team team = BeanCopy.convert(Team.class,parameter.getParamString());
        team.setId(id);
        int rs = 0;//ServiceFactory.instance().update(team);
        AssertUtils.isTrue(rs > 0,"操作失败");
        return rs;
    }


    @Delete("{id}")
    public Object delete(@RequestParam("id") String id, Parameter parameter){
        int rs = 1;//ServiceFactory.instance().deleteTeam(id);
        AssertUtils.isTrue(rs > 0,"操作失败");
        return rs;
    }

    public Object save(Parameter parameter){
        String id = parameter.getParamString().get("id");
        if(org.apache.commons.lang3.StringUtils.isEmpty(id)){
            return create(parameter);
        }
        return update(id,parameter);
    }


}
