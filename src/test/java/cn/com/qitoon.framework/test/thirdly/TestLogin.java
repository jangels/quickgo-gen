package cn.com.qitoon.framework.test.thirdly;

import com.quickgo.platform.face.ILoginService;
import com.quickgo.platform.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootApplication
public class TestLogin {
    @Autowired
    private ILoginService loginService;
    @Test
    public void login(){
        User user = loginService.findUser("test@123.com","1def1206947c01936f2600f63cb0abe6");
        user.getEmail();
        user.setNickname("");
    }
}
