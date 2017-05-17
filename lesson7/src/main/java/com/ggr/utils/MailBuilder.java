package com.ggr.utils;

import java.io.InputStream;

/**
 * Created by GuiRunning on 2017/5/17.
 * 定义自定义邮件的规范
 */
public interface MailBuilder {
    public InputStream builderMail()throws Exception;
}
