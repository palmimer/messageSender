/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Authority;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.RegistrationDTO;
import com.progmatic.messagesender.service.AlreadyExistsException;
import com.progmatic.messagesender.service.UserServiceImpl;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    private UserServiceImpl userService;
    
    @Autowired
    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
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
//        List<Authority> userAuthorities = new ArrayList<>();
        try {
            
            userService.createUser(registration);
            
        } catch (AlreadyExistsException ex) {
            bindingResult.rejectValue("username", "registration.username", "Már van ilyen felhasználó! Válasszon másik nevet!");
            return "register";
        }
        return "redirect:/messages/login";
    }
    
    private boolean isRegistrationValid(RegistrationDTO registration, BindingResult bindingResult){
        
        if ( !registration.getPassword1().equals(registration.getPassword2()) ){
            bindingResult.rejectValue("password2", "registration.password2", "A jelszó nem egyezik");
            return false;
        } else {
            return true;
        }
    }
    
}
