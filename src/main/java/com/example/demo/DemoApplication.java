package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing

public class DemoApplication {
    //TODO: Fix in-line styling in add-wish?
    //TODO: Evt add noget diverse errorhåndtering og andre @ til entities
    //TODO: ADD DTO til entities!
    //TODO: FIND A WAY TO COPY LINK WHEN VIEWING WISHES
    //HIDE PASSWORD IN APPLICATION.properties mudafukaaaa
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
