package com.Lab4.ProductManagementSystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.Lab4.ProductManagementSystem.interceptor.LoggingInterceptor;
import com.Lab4.ProductManagementSystem.interceptor.AuthenticationInterceptor;
import com.Lab4.ProductManagementSystem.util.StringToProductConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
        registry.addInterceptor(new AuthenticationInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToProductConverter());
    }
}