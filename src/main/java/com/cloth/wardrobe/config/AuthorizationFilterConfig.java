package com.cloth.wardrobe.config;

import com.cloth.wardrobe.filter.ApiAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AuthorizationFilterConfig {

    @Autowired
    private ApiAuthorizationFilter apiAuthorizationFilter;

    @Bean
    public FilterRegistrationBean<Filter> apiAuthorization() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(apiAuthorizationFilter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/api/v1/*");

        return filterRegistrationBean;
    }
}
