package com.ggr.domain;

import com.ggr.utils.PropConfigUtil;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by GuiRunning on 2017/5/17.
 * 邮件发送器
 */
public class MailSender {

    private static MailEntity mail = new MailEntity();

    /**
     * @param title 邮件的标题内容
     * @return 包含了标题的邮件
     */
    public MailSender title(String title) {
        mail.setTitle(title);
        return this;
    }

    /**
     * @param content
     * @return
     */
    public MailSender content(String content) {
        mail.setContent(content);
        return this;
    }

    /**
     * 设置邮件格式
     *
     * @param typeEnum
     * @return
     */
    public MailSender contentType(MailContentTypeEnum typeEnum) {
        mail.setContentType(typeEnum.getContentType());
        return this;
    }

    /**
     * @param set 目标邮箱地址
     * @return
     */
    public MailSender target(HashSet<String> set) {
        mail.setSet(set);
        return this;
    }

    /**
     * 通用部分抽离
     */
    private MimeMessage init(){
        //默认使用html内容发送
        if (mail.getContentType() == null)
            mail.setContentType(MailContentTypeEnum.HTML.getContentType());
        //读取/resource/mail_zh_CN.properties文件内容
        final PropConfigUtil properties = new PropConfigUtil("mail");
        // 创建Properties 类用于记录邮箱的一些属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", properties.getValue("mail.smtp.service"));
        //设置端口号，QQ邮箱给出了两个端口465/587
        props.put("mail.smtp.port", properties.getValue("mail.smtp.port"));
        // 设置发送邮箱
        props.put("mail.user", properties.getValue("mail.from.address"));
        // 设置发送邮箱的16位STMP口令
        props.put("mail.password", properties.getValue("mail.from.smtp.pwd"));

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        String nickName = null;
        InternetAddress form=null;
        try {
            nickName = MimeUtility.encodeText(properties.getValue("mail.from.nickname"));
            form = new InternetAddress(nickName + " <" + props.getProperty("mail.user") + ">");
            message.setFrom(form);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (AddressException e1) {
            e1.printStackTrace();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        return message;
    }

    /**
     * 抽离公共部分
     * @param msg
     */
    private void fun( MimeMessage msg){
        MimeMessage message = msg;
        //发送邮箱地址
        System.out.println("发送邮箱地址");
        HashSet<String> targets = mail.getSet();
        Iterator<String> iterator = targets.iterator();

        while (iterator.hasNext()){
            try {
                // 设置收件人的邮箱
                InternetAddress to = new InternetAddress(iterator.next());
                message.setRecipient(Message.RecipientType.TO, to);
                // 最后当然就是发送邮件啦
                Transport.send(message);

            } catch (Exception e) {
                continue;
            }
        }
    }

    /**这是一个简单的邮件格式的发送
     * @return 是否发送
     * 成功 true
     * 失败 false
     */
    public boolean sender() {

        MimeMessage message = init();

        // 设置邮件标题
        try {
            message.setSubject(mail.getTitle());
            //html发送邮件
            if (mail.getContentType().equals(MailContentTypeEnum.HTML.getContentType())) {
                // 设置邮件的内容体
                message.setContent(mail.getContent(), mail.getContentType());
            }
            //文本发送邮件
            else if (mail.getContentType().equals(MailContentTypeEnum.TEXT.getContentType())) {
                message.setText(mail.getContent());
            }
        } catch (AddressException e) {
            e.printStackTrace();
            return false;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        fun(message);
        return true;
    }

    public static  void main(String[] args){
        HashSet set = new HashSet<String>();
        set.add("3095764372@qq.com");
        boolean flag = new MailSender().content("测试一下").contentType(MailContentTypeEnum.HTML).title("springboot").target(set).sender();

    }
}
