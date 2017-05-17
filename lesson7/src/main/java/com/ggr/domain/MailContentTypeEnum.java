package com.ggr.domain;

/**
 * Created by GuiRunning on 2017/5/17.
 */
public enum MailContentTypeEnum {
    HTML("text/html;charset=utf-8"),
    TEXT("text");
    private String contentType;
    private MailContentTypeEnum(String contentType){
        this.contentType=contentType;
    }
    public String getContentType(){
        return this.contentType;
    }
}
