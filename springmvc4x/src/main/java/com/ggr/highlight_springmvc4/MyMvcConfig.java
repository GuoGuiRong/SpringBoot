package com.ggr.highlight_springmvc4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by GuiRunning on 2017/5/21.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.ggr.highlight_springmvc4")
public class MyMvcConfig {
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        /**
         * 因为运行时main/resources/views/ 下的文件会自动编译到 WEB-INF/classes/views/
         * 所以这里解析的时WEB-INF/classes/views/下的.jsp文件
         */
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }
}
