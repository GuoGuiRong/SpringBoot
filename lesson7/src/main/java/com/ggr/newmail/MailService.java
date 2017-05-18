package com.ggr.newmail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * Created by GuiRunning on 2017/5/18.
 * springboot邮件服务模块的使用
 */
@Service
public class MailService {

    //开启日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //发送器,
    @Autowired
    private JavaMailSender sender;

    /*
     * 这里提供了两种网页渲染方案,freeMarker和velocity
     */

    @Autowired
    private Configuration configuration; //freeMarker configuration*/

   /* @Autowired
    private VelocityEngine velocityEngine;//velocity VelocityEngine*/

    //邮件的发件人
    @Value("${spring.mail.username}")
    private String from;


    /**
     * 发送纯文本的简单邮件
     * @param to 接受方
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            sender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
    }

    /**
     * 发送html格式的邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String to, String subject, String content){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            sender.send(message);
            logger.info("html邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 发送带附件的邮件
     * @param to  邮件的收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param filePaths 多个附件数组，例如new String[]{"a.txt","b.pdf"}
     */
    public void sendAttachmentsMail(String to, String subject, String content, String[] filePaths){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            for (String file:filePaths) {
                File f = new File(file);
                FileSystemResource tempfile = new FileSystemResource(f);
                String fileName = f.getName();
                System.out.println(fileName);
                helper.addAttachment(fileName, tempfile);
            }

            sender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
    }

    /**
     * 发送嵌入静态资源（一般是图片）的邮件
     * @param to 邮件的收件人
     * @param subject 邮件主题
     * @param content 邮件内容，需要包括一个静态资源的id，cid:是固定的写法,比如：<img src=\"cid:rscId01\" >
     * @param rscPath 静态资源路径和文件名
     * @param rscId 静态资源id
     *
     *   这里需要注意的是 addInline 函数中资源名称 text 需要与正文中 cid:text 对应起来
     */
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId){
        MimeMessage message = sender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);

            sender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
    }

    /**
     *
     * @param to 收件人
     * @param subject 主题
     * @param template 模板文件
     * @param model 动态数据
     */
    public void sendTemplateMail(String to, String subject,String template,Map<String, Object> model){

        MimeMessage message = sender.createMimeMessage();

        //true表示需要创建一个multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            String text=null;

            /* //获取模板的后缀
            int i = template.lastIndexOf(".");
            String temp = template.substring(i);
            if("vm".equals(temp)){
                text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template.vm", "UTF-8", model);
            }else{}

            */

            Template t = configuration.getTemplate(template); // freeMarker template
            text= FreeMarkerTemplateUtils.processTemplateIntoString(t,model);


            //将渲染结果给邮件内容
            helper.setText(text, true);

            sender.send(message);
            logger.info("模板邮件已经发送。");
        } catch (Exception e){
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }

    }
}
