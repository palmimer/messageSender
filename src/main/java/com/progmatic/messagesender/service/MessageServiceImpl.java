/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.SenderComparator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author progmatic
 */

@Service

public class MessageServiceImpl {
    
    private List<Message> messages;
    
    public MessageServiceImpl() {
        this.messages = new ArrayList<>();;
        
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
    
    public List<Message> listMessages(int messageCountToShow, boolean inOrder){
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
        return shortList;
    }

    public List<Message> getMessages() {
        return messages;
    }
    
    public Message getSingleMessage(int messageId){
        Map<Integer, Message> messagesWithIds = makeMapWithIds();
        return messagesWithIds.get(messageId);
    }
    
    private Map<Integer, Message> makeMapWithIds() {
        Map<Integer, Message> mapWithIds = new HashMap<>();
        for (Message message : messages) {
            mapWithIds.put(message.getId(), message);
        }
        return mapWithIds;
    }
    
    public void addNewMessage(Message message) {
        messages.add(message);
    }

    
    
    
    
}