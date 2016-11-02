package com.quickgo.platform.face;

import com.quickgo.platform.model.User;


public interface ILoginService {
    User findUser(String email,String password);
}
