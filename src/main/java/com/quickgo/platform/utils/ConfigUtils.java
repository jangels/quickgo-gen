package com.quickgo.platform.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * @author huangjie
 * @date 2016/10/22
 */
public class ConfigUtils {
    private static Logger log = Logger.getLogger(ConfigUtils.class);

    private static Properties properties;


    static {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            //properties.setProperty("mango.view.json","org.mangoframework.core.view.JsonView");
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static void init(String file) {
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static String getProperty(String loginUrl) {
        return properties.getProperty(loginUrl);
    }


    public static String getErrorPage() {
        return properties.getProperty("mango.errorpage");
    }


    public static boolean getRequestEscape() {
        return Boolean.parseBoolean(properties.getProperty("mango.escape", "true"));
    }

    public static int getTokenExpires() {
        return Integer.parseInt(properties.getProperty("token.expires"));
    }

    public static String getSalt() {
        return properties.getProperty("salt");
    }

    public static String getFileUploadDir() {
        return properties.getProperty("file.upload.dir");
    }

    public static String getFileAccessURL() {
        return properties.getProperty("file.access.url");
    }

    public static String getBucketURL() {
        return properties.getProperty("file.qiniu.bucket");
    }

    public static String getUploadServer() {
        return properties.getProperty("file.upload.server", "owner");
    }

    public static String getQiniuAccessKey() {
        return properties.getProperty("file.qiniu.accessKey");
    }

    public static String getQiniuSecretKey() {
        return properties.getProperty("file.qiniu.secretKey");
    }

    public static String getQQAppId() {
        return properties.getProperty("qq.appid");
    }

    public static String getQQAppKey() {
        return properties.getProperty("qq.appkey");
    }

    public static String getWelcomeFile() {
        return properties.getProperty("mango.welcome");
    }

    public static String getControllerPrefix() {
        return properties.getProperty("mango.controller.prefix");
    }

    public static String getControllerPrefix(String name) {
        return properties.getProperty("mango.controller.prefix." + name);
    }

    public static String getMailHost() {
        return properties.getProperty("mail.smtp.host");
    }
    public static String getMailauth() {
        return properties.getProperty("mail.smtp.auth");
    }
    public static String getMaiprotocol() {return properties.getProperty("mail.transport.protocol");}
    public static int getPort() {return Integer.valueOf(properties.getProperty("mail.smtp.port"));}
    public static String getUsername() {return properties.getProperty("mail.username");}
    public static String getPassword() {return properties.getProperty("mail.password");}

    public  static final int PORT = Integer.valueOf(properties.getProperty("mail.smtp.port"));

}
