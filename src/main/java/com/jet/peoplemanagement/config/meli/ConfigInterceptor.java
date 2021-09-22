package com.jet.peoplemanagement.config.meli;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@RequiredArgsConstructor
@Component
public class ConfigInterceptor extends WebMvcConfigurerAdapter {

    private final IntercerptorMine inter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(inter);
    }

}