package com.brandon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
//@ImportResource("classpath:/feeds/feed-context.xml")
public class BasedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasedProjectApplication.class, args);
    }
}
