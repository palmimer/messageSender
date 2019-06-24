/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author progmatic
 */
public class Message {
    private int id;
    private static int previousId = 1;
    private String sender;
    private String text;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime sendingTime;

    public Message( String sender, String text, LocalDateTime sendingTime) {
        this.sender = sender;
        this.text = text;
        this.sendingTime = sendingTime;
        this.id = previousId ++;
    }

    public Message() {
        this.sendingTime = LocalDateTime.now();
        this.id = previousId ++;
    }
    
    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }
    
    
}
