package com.qitoon.framework.face;

import com.qitoon.framework.model.User;


public interface ILoginService {
    User findUser(String email,String password);
}
