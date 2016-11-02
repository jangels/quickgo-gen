package com.quickgo.platform.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import com.quickgo.platform.utils.LogTemplate;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * Created by Administrator on 2016/1/6.
 */
public class AjaxFormMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset UTF8 = Charset.forName("UTF-8");

    private Charset charset = UTF8;

    private SerializerFeature[] features = new SerializerFeature[0];

    public AjaxFormMessageConverter() {
        super(new MediaType[] { new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8) });
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public SerializerFeature[] getFeatures() {
        return this.features;
    }

    public void setFeatures(SerializerFeature[] features) {
        this.features = features;
    }

    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();

        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len == -1) {
                break;
            }

            if (len > 0) {
                baos.write(buf, 0, len);
            }
        }

        byte[] bytes = baos.toByteArray();
        String json = URLDecoder.decode(new String(bytes, UTF8), UTF8.name());
        LogTemplate.info("请求参数：{}", json);
        if(json.endsWith("=")) {
            json = json.substring(0,json.lastIndexOf("="));
        }
        return JSON.parseObject(json, clazz);
    }

    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String text = JSON.toJSONString(obj, this.features);
        LogTemplate.info("返回数据：{}", text);
        byte[] bytes = text.getBytes(this.charset);
        out.write(bytes);
    }


}