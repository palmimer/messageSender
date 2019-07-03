/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author progmatic
 */
@Entity
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String sender;
    
    private String text;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime sendingTime;
    
    private boolean isDeleted;
    
    @ManyToOne
    private Topic topic;

    public Message( String sender, String text, LocalDateTime sendingTime) {
        this.sender = sender;
        this.text = text;
        this.sendingTime = sendingTime;
        this.isDeleted = false;
    }

    public Message() {
        this.sendingTime = LocalDateTime.now();
        this.isDeleted = false;
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
    
    public void setToDelete(){
        this.isDeleted = true;
    }
    
    public void setNotToDelete(){
        this.isDeleted = false;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }
    
    
}
