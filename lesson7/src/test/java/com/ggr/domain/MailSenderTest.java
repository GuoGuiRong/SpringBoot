package com.ggr.domain;

import org.junit.Test;

import java.util.HashSet;

/**
 * Created by GuiRunning on 2017/5/17.
 */
public class MailSenderTest {
    @Test
    public void sender() throws Exception {
        HashSet set = new HashSet<String>();
        set.add("3095764372@qq.com");
        boolean flag = new MailSender().content("测试模块").contentType(MailContentTypeEnum.HTML).title("springboot").target(set).sender();
    }

}