/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;

/**
 *
 * @author progmatic
 */
public class Message {
    private int id;
    private static int previousId = 1;
    private String sender;
    private String text;
    private LocalDateTime sendingTime;

    public Message( String sender, String text, LocalDateTime sendingTime) {
        this.sender = sender;
        this.text = text;
        this.sendingTime = sendingTime;
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

    
}
