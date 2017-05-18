package com.ggr.newmail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * Created by GuiRunning on 2017/5/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void sendSimpleMail() throws Exception {
        mailService.sendSimpleMail("3095764372@qq.com","简单邮件","这是简单邮件测试内容");
    }

    @Test
    public void sendHtmlMail() throws Exception {
        mailService.sendHtmlMail("3095764372@qq.com","html格式的邮件","<marqueue>html格式的邮件</marqueue>");
    }

    @Test
    public void sendAttachmentsMail() throws Exception {
        mailService.sendAttachmentsMail( "3095764372@qq.com","发送带附件的邮件","发送带附件的邮件",new String[]{"src/main/resources/static/image/xxy040.png","src/main/resources/static/知识点.txt"});
    }

    @Test
    public void sendInlineResourceMail() throws Exception {
       // mailService.sendHtmlMail("3095764372@qq.com","发送嵌入静态资源（一般是图片）的邮件","<marqueue>html格式的邮件</marqueue>");
        mailService.sendInlineResourceMail( "3095764372@qq.com","发送带内嵌文件的邮件","<html><img src='cid:xxy' ></html>","src/main/resources/static/image/xxy033.png","xxy");
    }


    @Test
    public void sendTemplateMail() throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("to","3095764372@qq.com");
        map.put("note","I love you");
        mailService.sendTemplateMail("3095764372@qq.com","模板邮件","temp.ftl",map);
    }

}