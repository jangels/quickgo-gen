package com.quickgo.platform.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;



public class SwaggerConfig
{

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }


    public SwaggerSpringMvcPlugin customImplementation(){
        return  new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(new ApiInfo("api", "desc", null, null, null, null))
                .useDefaultResponseMessages(false)
                .includePatterns("/users.*");
    }
}