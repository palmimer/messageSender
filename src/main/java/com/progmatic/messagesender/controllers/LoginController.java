/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.RegistrationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author progmatic
 */

@Controller
public class LoginController {
    
    private UserDetailsManager userDetailsService;
    
    @Autowired
    public LoginController(UserDetailsService userDetailsService) {
        this.userDetailsService = (UserDetailsManager) userDetailsService;
    }
    
    
     
    @GetMapping("messages/login")
    public String showLoginPage(){
        return "login";
    }
    
    
    @GetMapping("messages/register")
    public String showRegisterPage(
        @ModelAttribute("registration") RegistrationDTO registration){
        return "register";
    }
    
    @PostMapping("messages/newregistration")
    public String register(
            @Valid
            @ModelAttribute("registration") RegistrationDTO registration, BindingResult bindingResult){
        
        if ( bindingResult.hasErrors() || !isRegistrationValid(registration, bindingResult)) {
            return "register";
        }
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        RegisteredUser newUser = new RegisteredUser(
                registration.getUserName(), registration.getPassword1(), 
                registration.getEmail(), userAuthorities);
        userDetailsService.createUser(newUser);
        return "login";
    }
    
    private boolean isRegistrationValid(RegistrationDTO registration, BindingResult bindingResult){
        if ( userDetailsService.userExists(registration.getUserName()) ) {
            bindingResult.rejectValue("username", "registration.username", "Már van ilyen felhasználó! Válasszon másik nevet!");
            return false;
        } else if ( !registration.getPassword1().equals(registration.getPassword2()) ){
            bindingResult.rejectValue("password2", "registration.password2", "A jelszó nem egyezik");
            return false;
        } else {
            return true;
        }
    }
}
