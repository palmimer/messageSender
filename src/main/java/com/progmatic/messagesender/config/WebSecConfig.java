/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 *
 * @author progmatic
 */


@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin()
                .loginPage("/messages/login")
                .defaultSuccessUrl("/messages", true)
                .permitAll()
                .and()
                .logout()
                .and()
                  
                .authorizeRequests()
                .antMatchers("/messages", "/login", "/css/*" ).permitAll()
                .antMatchers("/messages/register", "/messages/newregistration").permitAll()
                // ezt az oldalt csak bejelentkezett felhasználó érheti el
                //.antMatchers("/messages/writenew").access("hasRole('USER') or hasRole('ADMIN')")
                .anyRequest().authenticated();
    }
    
    
    
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Merci")
                .password("merci").roles("USER").build());
        manager.createUser(User.withUsername("admin")
                .password("pw").roles("ADMIN").build());
        return manager;
    }
    
    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    
    
}
