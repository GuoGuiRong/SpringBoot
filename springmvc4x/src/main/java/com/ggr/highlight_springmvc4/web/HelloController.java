package com.ggr.highlight_springmvc4.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by GuiRunning on 2017/5/21.
 */
@Controller
public class HelloController {

    @RequestMapping("/index")
    public String hello(){

       return "index";

    }
}
