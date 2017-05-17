package com.ggr.domain;


import java.util.HashSet;

/**
 * Created by GuiRunning on 2017/5/17.
 * 定义邮件实体类
 */
public class MailEntity {

    private String stmpService;//邮箱服务器
    private String stmpPort;//服务端口
    private String fromMailAddress;//发送方邮箱地址
    private String stmpPwd;//认证口令
    private String title;//邮件标题
    private String content;//邮箱内容
    private String contentType = MailContentTypeEnum.HTML.getContentType();//内容类型
    private HashSet<String> set;//要发送邮件地址集合

    public MailEntity(){}
    public String getStmpService() {
        return stmpService;
    }

    public void setStmpService(String stmpService) {
        this.stmpService = stmpService;
    }

    public String getStmpPort() {
        return stmpPort;
    }

    public void setStmpPort(String stmpPort) {
        this.stmpPort = stmpPort;
    }

    public String getFromMailAddress() {
        return fromMailAddress;
    }

    public void setFromMailAddress(String fromMailAddress) {
        this.fromMailAddress = fromMailAddress;
    }

    public String getStmpPwd() {
        return stmpPwd;
    }

    public void setStmpPwd(String stmpPwd) {
        this.stmpPwd = stmpPwd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HashSet<String> getSet() {
        return set;
    }

    public void setSet(HashSet<String> set) {
        this.set = set;
    }
    @Override
    public String toString() {
        return "MailEntity{" +
                "stmpService='" + stmpService + '\'' +
                ", stmpPort='" + stmpPort + '\'' +
                ", fromMailAddress='" + fromMailAddress + '\'' +
                ", stmpPwd='" + stmpPwd + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", contentType='" + contentType + '\'' +
                ", set=" + set +
                '}';
    }
}
