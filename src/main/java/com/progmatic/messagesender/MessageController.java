/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        Message m5 = new Message("Anyu", "Megtaláltam az esőkabátodat.", LocalDateTime.now());
        Message m6 = new Message("Lilla", "Akkor jössz ma este?", LocalDateTime.now());
        Message m7 = new Message("Lajos", "Holnap jó idő lesz, találkozunk?", LocalDateTime.now());
        Message m8 = new Message("Kata", "Jó, akkor talizunk 4-kor a szökőkútnál.", LocalDateTime.now());
        messages.add(m1);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        messages.add(m5);
        messages.add(m6);
        messages.add(m7);
        messages.add(m8);
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String listMessages(
            @RequestParam(value = "mc", defaultValue = "4") int messageCountToShow,
            @RequestParam(value = "order", defaultValue = "false") boolean inOrder,
            Model model){
        
        if(messageCountToShow < 0) {
            messageCountToShow = messages.size();
        }
        
        List<Message> messagesInOrder;
        if (inOrder) {
            messagesInOrder = messages.stream().sorted(new SenderComparator()).collect(Collectors.toList());
        } else {
            messagesInOrder = messages;
        }
                                        // Math.min() amelyik a kisebb érték, azt adjuk meg a sublist végső értékének
        List<Message> shortList = messagesInOrder.subList(0, Math.min(messages.size(), messageCountToShow));
        model.addAttribute("messages", shortList);
        return "messages";
    }
    
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public String showSingleMessage(
            @PathVariable("messageId") int messageId, Model model){
        Map<Integer, Message> messagesWithIds = makeMapWithIds(messages);
        Message singleMessageToShow = messagesWithIds.get(messageId);
        model.addAttribute("message", singleMessageToShow);
        return "singlemessage";
    }

    private Map<Integer, Message> makeMapWithIds(List<Message> messages) {
        Map<Integer, Message> mapWithIds = new HashMap<>();
        for (Message message : messages) {
            mapWithIds.put(message.getId(), message);
        }
        return mapWithIds;
    }
    
}
