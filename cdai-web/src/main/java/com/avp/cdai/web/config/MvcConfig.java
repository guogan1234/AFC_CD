package com.avp.cdai.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Hongfei
 */
@Configuration
@SessionAttributes("authorizationRequest")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // main -- dashboard
        registry.addViewController("/index.html").setViewName("cdai/main");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }
}
