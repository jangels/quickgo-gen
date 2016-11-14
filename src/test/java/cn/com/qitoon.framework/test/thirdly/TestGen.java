package cn.com.qitoon.framework.test.thirdly;

import com.quickgo.platform.Application;
import com.quickgo.platform.face.IInterfaceService;
import com.quickgo.platform.model.Interface;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjie
 * on 2016/11/10.
 */
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestGen {

    @Autowired
    private IInterfaceService interfaceService;

    @Test
    public void findList(){
        List<String> ids = new ArrayList<>();
        ids.add("3yIbIiyxc");
        ids.add("HhqE3yhE");
        ids.add("HjG5dyld");
        List<Interface> list = interfaceService.getInterfacesByIds(ids);
        System.out.println(list.size());
    }
}
