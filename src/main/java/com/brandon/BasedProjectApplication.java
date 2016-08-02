package com.brandon;

import com.brandon.entities.EntityBased;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class})
@EntityScan(basePackageClasses = EntityBased.class)
public class BasedProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasedProjectApplication.class, args);
    }
}
