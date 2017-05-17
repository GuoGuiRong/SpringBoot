package com.ggr.utils;

import com.ggr.domain.MailSender;
import org.junit.Test;

import java.io.File;

/**
 * Created by GuiRunning on 2017/5/17.
 */
public class MailBuilderImplTest {
    @Test
    public void builderMail() throws Exception {
        MailBuilderImpl mailBuilder = new MailBuilderImpl();
        /**
         * 添加附件
         */
        File[] files = new File[2];
        files[0]=new File("src/main/resources/static/知识点.txt");
        files[1]=new File("src/main/resources/static/image/xxy001.png");
        mailBuilder.setFiles(files);

        /**
         * 内嵌图片
         */
        mailBuilder.setImageFiles(new File("src/main/resources/static/image/xxy001.png"));

        mailBuilder.builderMail();

        /*HashSet set = new HashSet<String>();
        set.add("3095764372@qq.com");*/
        //boolean flag = new MailSender().content("测试一下").contentType(MailContentTypeEnum.HTML).title("springboot").target(set).sender();
        new MailSender().senderComplex(mailBuilder);
    }

}