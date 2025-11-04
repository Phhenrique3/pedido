package com.example.pedido.config;

import com.example.pedido.filter.MDCFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MDCFilter> mdcFilterRegistration(MDCFilter mdcFilter) {
        FilterRegistrationBean<MDCFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(mdcFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
