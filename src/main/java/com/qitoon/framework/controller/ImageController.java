package com.qitoon.framework.controller;

import com.alibaba.fastjson.JSON;
import com.qitoon.framework.exception.ImageData;
import com.qitoon.framework.param.Parameter;
import com.qitoon.framework.param._HashMap;
import com.qitoon.framework.utils.AssertUtils;
import com.qitoon.framework.utils.ConfigUtils;
import com.qitoon.framework.utils.FileUtils;
import com.qitoon.framework.view.ByteArrayView;
import com.qitoon.framework.view.StringView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : huangjie
 * @since : 16/6/1
 */
@RestController
@RequestMapping("/image")
public class ImageController {
    private static Logger logger = Logger.getLogger(ImageController.class);

    @RequestMapping("/baiduupload")
    public StringView upload(Parameter parameter) throws IOException {
        HttpServletResponse response = parameter.getResponse();
        response.setContentType("text/html;charset=utf-8");
        List<FileItem> images = parameter.getParamFile().get("upfile");
        AssertUtils.isTrue(images != null && images.size() > 0, "内容为空");
        List<String> responses = new ArrayList<>();
        long size = 0;
        for (FileItem item : images) {
            try {
                ImageData imageData = FileUtils.uploadImage(item);
                responses.add(imageData.getPath());
                size = imageData.getSize();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                String jsonstr = JSON.toJSONString(new _HashMap<>().add("state", "ERROR").add("message", e.getMessage()));
                return new StringView(jsonstr);
            }
        }
        String res = responses.get(0);
        String str = JSON.toJSONString(new _HashMap<>().add("name", res).add("size", size).add("state", "SUCCESS").add("type", ".jpg").add("url",
                ConfigUtils.getFileAccessURL() + res));
        return new StringView(str);
    }

//    @Get("proxy")
    @RequestMapping("/proxy")
    public ByteArrayView proxy(Parameter parameter) throws IOException {
        String target = parameter.getParamString().get("target");
        if (StringUtils.isNotBlank(target)) {
            URL url = new URL(target);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            connection.setRequestProperty("Host", url.getHost());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            parameter.getResponse().setContentType("image/jpeg");
            IOUtils.copy(connection.getInputStream(), baos);
            return new ByteArrayView(baos.toByteArray());
        }
        return null;
    }
}
