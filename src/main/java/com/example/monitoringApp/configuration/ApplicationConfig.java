package com.example.monitoringApp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Configuration
@EnableWebMvc
public class ApplicationConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("static/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ScheduledExecutorService scheduledExecutor() {
        return Executors.newScheduledThreadPool(1);
    }
}
