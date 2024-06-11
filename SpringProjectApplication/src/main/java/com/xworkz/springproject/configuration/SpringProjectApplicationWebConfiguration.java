package com.xworkz.springproject.configuration;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.xworkz")
@PropertySource("classpath:application.properties")
public class SpringProjectApplicationWebConfiguration implements WebMvcConfigurer {
    public SpringProjectApplicationWebConfiguration(){
        System.out.println("Running SpringProjectApplicationWebConfiguration");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/linkFiles/");
    }
}
