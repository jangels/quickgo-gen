package com.quickgo.platform.utils;


import com.quickgo.platform.model.EmailContent;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;


import java.util.List;
import java.util.Properties;

/**
 * Created by 146336
 * on 2016/11/10.
 */
public class SendMailUtils {
    private static Logger logger = Logger.getLogger(SendMailUtils.class);
    public  static boolean sendEmail(EmailContent emailContent, Address[] to) {
        //定义Properties对象,设置环境信息
        Properties props = new Properties();
        //设置邮件服务器的地址
        props.setProperty("mail.transport.protocol","smtp"); // 指定的smtp服务器
        props.setProperty("mail.smtp.host","smtp.163.com");
        props.setProperty("mail.smtp.auth","true");//设置发送邮件使用的协议
        props.setProperty("mail.smtp.port","25");
        //创建Session对象,session对象表示整个邮件的环境信息
        Session session = Session.getInstance(props);
        //设置输出调试信息
        session.setDebug(true);
        try {
            //Message的实例对象表示一封电子邮件
            MimeMessage message = new MimeMessage(session);
            //设置发件人的地址
            message.setFrom(new InternetAddress(ConfigUtils.getUsername()));
            //设置主题
            message.setSubject(emailContent.getTitle());
            //设置邮件的文本内容
            //message.setText(emailMsg);
            message.setContent(emailContent.getContent(),"text/html;charset=UTF-8");

            //从session的环境中获取发送邮件的对象
            Transport transport = session.getTransport();
            //连接邮件服务器
            transport.connect(ConfigUtils.getMailHost(),ConfigUtils.PORT, ConfigUtils.getUsername(), ConfigUtils.getPassword());

            //设置收件人地址,并发送消息
            transport.sendMessage(message, to);
            transport.close();
            return true;
        } catch (MessagingException e) {
            logger.warn("邮件发送异常", e);
            return false;
        }
    }
}
