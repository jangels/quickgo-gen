package com.quickgo.platform.service;

import com.fasterxml.jackson.databind.JavaType;
import com.quickgo.platform.exception.InvalidArgumentException;
import com.quickgo.platform.face.IGenApiCodeService;
import com.quickgo.platform.face.IInterfaceService;
import com.quickgo.platform.model.Interface;
import com.quickgo.platform.param.RequestArgs;
import com.quickgo.platform.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
public class GenApiCodeService implements IGenApiCodeService {

    @Autowired
    private IInterfaceService interfaceService;
    @Override
    public int genApiCode(List<String> fids, String packageName) {
        //根据分类Id查询所选表下面的所有接口
        List<Interface> interfaceList = interfaceService.getInterface(fids.get(0));

        if(CollectionUtils.isEmpty(interfaceList)){
            throw  new InvalidArgumentException("参数无效");
        }
        for(Interface face :interfaceList){
            String requestArgs = face.getRequestArgs();
            //将入参json转化为对象
            JavaType javaType = JsonUtil.getCollectionType(ArrayList.class, ArrayList.class,RequestArgs.class);
            List<RequestArgs> requestArgsList = null;
            try {
                requestArgsList =  (List<RequestArgs>)JsonUtil.getMapper().readValue(requestArgs, javaType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(CollectionUtils.isEmpty(requestArgsList)){
                continue;
            }else {
                //
                for (RequestArgs requesArgs:requestArgsList) {
                    if(requesArgs.getType().contains("Object")){

                    }
                }
            }



        }


        return 0;
    }
}
