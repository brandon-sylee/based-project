package com.brandon.configurations;

import com.brandon.author.SimpleHierarchyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by brandon Lee on 2016-08-25.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/board/list/**").permitAll()
                .antMatchers("/favicon.ico", "/rs/**", "/font/**", "/css/**", "/js/**", "/images/**", "/webjars/**", "/api/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf()
                .ignoringAntMatchers("/api/ws/**")
            .and()
                .headers().frameOptions().sameOrigin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("dd.stay.hf@gmail.com").password("password").authorities(SimpleHierarchyRole.valueOf("ROLE_MASTER").authorities());
    }

}
