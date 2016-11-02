package com.qitoon.framework.controller;


import com.qitoon.framework.annotation.Post;
import com.qitoon.framework.model.Team;
import com.qitoon.framework.model.User;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.utils.AssertUtils;
import com.qitoon.framework.utils.BeanCopy;
import com.qitoon.framework.utils.MemoryUtils;
import com.qitoon.framework.utils.Validate;
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

    @Post
    public Object create(Parameter parameter){
        User user = MemoryUtils.getUser(parameter);
        Team team = BeanCopy.convert(Team.class,parameter.getParamString());
        AssertUtils.notNull(team.getName(),"missing name");
        team.setId(Validate.id());
        team.setCreateTime(new Date());
        team.setUserId(user.getId());
        team.setStatus(Team.Status.VALID);
        int rs =0;// ServiceFactory.instance().create(team);
        AssertUtils.isTrue(rs > 0,"操作失败");
        return team.getId();
    }


    @Post("{id}")
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

    @Post("save")
    public Object save(Parameter parameter){
        String id = parameter.getParamString().get("id");
        if(org.apache.commons.lang3.StringUtils.isEmpty(id)){
            return create(parameter);
        }
        return update(id,parameter);
    }


}
