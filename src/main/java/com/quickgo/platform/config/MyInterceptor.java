package com.quickgo.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by huangjie
 * on 2016/10/27.
 */
@Configuration
public class MyInterceptor extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CredentialsInterceptor());
        super.addInterceptors(registry);
    }
}
