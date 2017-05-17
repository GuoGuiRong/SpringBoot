package com.ggr.utils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.Properties;

/**
 * Created by GuiRunning on 2017/5/17.
 * 构建复杂邮件的实现类
 */
public class MailBuilderImpl implements MailBuilder{

    private File[] files;//附件
    private File imageFiles;//内嵌图片文件

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public File getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(File imageFiles) {
        this.imageFiles = imageFiles;
    }

    /**
     * @return 返回待填入数据的邮件对象
     */
    private MimeMessage getMimeMessage() throws UnsupportedEncodingException, MessagingException {
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
        String nickName = MimeUtility.encodeText(properties.getValue("mail.from.nickname"));
        //发件人邮箱地址
        InternetAddress form = new InternetAddress(nickName + " <" + props.getProperty("mail.user") + ">");

        message.setFrom(form);

        message.setSubject("测试专用数据");

        return message;
    }

    @Override
    public InputStream builderMail() throws IOException, MessagingException {

        MimeMessage message = getMimeMessage();
        //整封邮件的MINE消息体
        MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系

        //设置邮件的MINE消息体
        message.setContent(msgMultipart);

        MimeBodyPart content = new MimeBodyPart();
        /**
         * 添加附件
         */
        for (File file:this.files) {
            //附件1
            MimeBodyPart attch = new MimeBodyPart();


            //把附件加入到 MINE消息体中
            msgMultipart.addBodyPart(attch);

            //把文件，添加到相应附件中

            //数据源
            DataSource ds = new MyFileDataSource(file);
            //数据处理器
            DataHandler dh = new DataHandler(ds);

            //设置第附件的数据
            attch.setDataHandler(dh);

            //设置第附件的文件名
            attch.setFileName(MimeUtility.encodeText(file.getName()));
        }


        /**
         * 设置正文内容
         */
        msgMultipart.addBodyPart(content);
        MimeMultipart bodyMultipart  = new MimeMultipart("related");

        //设置内容为正文
        content.setContent(bodyMultipart);

        //html代码部分
        MimeBodyPart htmlPart = new MimeBodyPart();

        //html中嵌套的图片部分
        MimeBodyPart imgPart = new MimeBodyPart();

        //正文添加图片和html代码
        bodyMultipart.addBodyPart(htmlPart);

        bodyMultipart.addBodyPart(imgPart);

        //把文件，添加到图片中
        DataSource imgds = new FileDataSource(this.getImageFiles());
        DataHandler imgdh = new DataHandler(imgds);
        imgPart.setDataHandler(imgdh);
        //说明html中的img标签的src，引用的是此图片
        imgPart.setHeader("Content-Location","http://sunteam.cc/logo.jsg");
        //html代码
        htmlPart.setContent("<span style='color:red'>ggr专用测试用例</span><img src=\"http://sunteam.cc/logo.jsg\">","text/html;charset=utf-8");

        //生成文件邮件
        message.saveChanges();

        //输出到临时文件
        OutputStream os = new FileOutputStream("src/main/resources/temp.eml",false);
        message.writeTo(os);
        os.close();

        //读取出来
        FileInputStream fis = new FileInputStream("src/main/resources/temp.eml");

        return fis;
    }

}
