/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author progmatic
 */
public class RegisteredUser extends User {
    private int userId;
    private static int prevUserId;
    private String email;
    private LocalDateTime registrationTime;

    public RegisteredUser(String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = prevUserId++;
        this.email = email;
        this.registrationTime = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistrationTime() {
        return registrationTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
    
}
