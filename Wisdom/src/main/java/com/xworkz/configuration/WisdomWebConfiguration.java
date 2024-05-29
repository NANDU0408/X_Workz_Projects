package com.xworkz.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.xworkz")
@PropertySource("classpath:application.properties")
public class WisdomWebConfiguration implements WebMvcConfigurer {
    public WisdomWebConfiguration(){
        System.out.println("Running WisdomWebInit");
    }

//    public ViewResolver viewResolver(){
//        System.out.println("Running ViewResolver");
//        return new InternalResourceViewResolver("/",".jsp");
//    }

    @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/linkFiles/");
    }
}
