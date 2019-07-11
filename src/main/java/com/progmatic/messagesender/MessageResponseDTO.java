/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author progmatic
 */
@XmlRootElement
public class MessageResponseDTO {
    
    private int id;
    
    private String text;
    
    private String senderName;
    
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime dateTime;
    
    private TopicResponseDTO topic;

    public MessageResponseDTO() {
    }

    public MessageResponseDTO(int id, String text, String senderName, LocalDateTime dateTime, TopicResponseDTO topic) {
        this.id = id;
        this.text = text;
        this.senderName = senderName;
        this.dateTime = dateTime;
        this.topic = topic;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TopicResponseDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicResponseDTO topic) {
        this.topic = topic;
    }
    
    
    
}
