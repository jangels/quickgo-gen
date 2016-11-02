package com.quickgo.platform.face;

import com.quickgo.platform.model.FindPassword;
import com.quickgo.platform.model.User;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IUserService {

    boolean checkEmail(String email);

    int create(User user);

    String getUserIdByEmail(String email);

    List<User> getAllUserByUserId(String userId);

    int findPassword(FindPassword findPassword);

    int updatePwd(User user);
}
