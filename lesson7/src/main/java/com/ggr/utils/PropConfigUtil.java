package com.ggr.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by GuiRunning on 2017/5/17.
 */
public class PropConfigUtil {
    private ResourceBundle resources;
    private String filename;

    public PropConfigUtil(String filename){
       this.filename=filename;
        Locale locale = new Locale("zh","CN");
        this.resources = ResourceBundle.getBundle(this.filename,locale);
    }
    public String getValue(String key){
        return this.resources.getString(key);
    }

    public static void main(String[] args){
        System.out.println(new PropConfigUtil("mail").getValue("mail.smtp.service"));
    }
}
