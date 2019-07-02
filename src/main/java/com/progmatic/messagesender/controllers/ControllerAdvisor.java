/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author progmatic
 */

@ControllerAdvice
public class ControllerAdvisor {
    
//    @ExceptionHandler(EntityNotFoundException.class)
//    public String handleErrors(EntityNotFoundException ex, Model model){
//        model.addAttribute("exceptionMessage", ex.getMessage());
//        return "error";
//    }
}
