
package com.avp.cdai.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebApplication {
    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            logger.debug(beanName);
//        }
    }
}

