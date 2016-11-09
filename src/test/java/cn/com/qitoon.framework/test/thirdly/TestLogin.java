package cn.com.qitoon.framework.test.thirdly;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quickgo.platform.Application;
import com.quickgo.platform.param.RequestArgs;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class TestLogin {

    @Test
    public  void  changeObject(){
        String json = "[{\"require\":\"false\",\"children\":[],\"type\":\"string\",\"name\":\"name\"},{\"require\":\"false\",\"children\":[],\"type\":\"string\",\"name\":\"logo\"},{\"require\":\"false\",\"children\":[{\"require\":\"true\",\"type\":\"string\",\"children\":[],\"name\":\"remark\"}],\"type\":\"object\",\"name\":\"logo\"}]";
        String json1 = "[{\"require\":\"false\",\"children\":[{\"require\":\"true\",\"type\":\"string\",\"children\":[],\"name\":\"email\"}],\"type\":\"object\",\"name\":\"name\"},{\"require\":\"false\",\"children\":[],\"type\":\"string\",\"name\":\"logo\"},{\"require\":\"false\",\"children\":[{\"require\":\"true\",\"type\":\"string\",\"children\":[],\"name\":\"remark\"}],\"type\":\"object\",\"name\":\"logo\"}]";
        String jsonstr = "[{\"children\":[{\"children\":[],\"name\":\"id\",\"type\":\"string\",\"require\":\"true\"},{\"children\":[],\"name\":\"name\",\"type\":\"string\",\"require\":\"true\"},{\"children\":[],\"name\":\"lastUpdateTime\",\"type\":\"number\",\"require\":\"true\"},{\"children\":[{\"require\":\"true\",\"children\":[],\"type\":\"string\",\"name\":\"id\"},{\"require\":\"true\",\"children\":[],\"type\":\"string\",\"name\":\"name\"},{\"require\":\"true\",\"children\":[],\"type\":\"string\",\"name\":\"static\"}],\"name\":\"createTime\",\"type\":\"object\",\"require\":\"true\"},{\"children\":[],\"name\":\"projectId\",\"type\":\"string\",\"require\":\"true\"},{\"children\":[],\"name\":\"folders\",\"type\":\"array\",\"require\":\"true\",\"description\":\"\"}],\"name\":\"module\",\"type\":\"object\",\"require\":\"true\"}]";
        String tableName = "module";
        StringBuilder inputParam = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();
        List<RequestArgs> beanList = null;
        try {
           beanList = mapper.readValue(json1, new TypeReference<List<RequestArgs>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将入参json转化为对象
        if(CollectionUtils.isEmpty(beanList)){

        }else {
            //提取入参
            for (RequestArgs requesArgs:beanList) {
                if(requesArgs.getType().contains("object")){
                    //首字母转化为大写
                    inputParam.append( tableName.replaceFirst(tableName.substring(0, 1),
                            tableName.substring(0, 1).toUpperCase()));
                    inputParam.append(" "+tableName +",");
                }else {
                    inputParam.append("String "+requesArgs.getName()+", ");
                }
            }
            String s = inputParam.toString().substring(0,inputParam.length()-1);
            System.out.println(s);
        }

    }

}
