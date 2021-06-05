package com.example.jwt.authprojectjwt.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigApi {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
