/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author progmatic
 */

@Controller
public class MessageController {
    
    private List<Message> messages;

    public MessageController() {
        messages = new ArrayList<>();
        
        Message m1 = new Message("Lacika", "Hívd fel légyszi anyut!", LocalDateTime.now());
        Message m2 = new Message("Géza", "Meg kellene beszélni valamit, hívj fel!", LocalDateTime.now());
        Message m3 = new Message("Anyu", "Hogy ment a meghallgatás?", LocalDateTime.now());
        Message m4 = new Message("Lajos", "Vigyél esernyőt!", LocalDateTime.now());
        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String messages(Model model){
        
        model.addAttribute("messages", messages);
        return "messages";
    }
    
}
