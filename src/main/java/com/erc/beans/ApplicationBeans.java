package com.erc.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class ApplicationBeans {

    @Bean
    public NoOpPasswordEncoder noOpPasswordEncoder() { // Исключительно в образовательных целоях
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
