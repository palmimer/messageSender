/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author progmatic
 */

@SpringBootApplication
@ComponentScan("com.progmatic.messagesender")
public class StartApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args );
    }
    
}
