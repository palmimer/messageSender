/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author progmatic
 */
@Entity
@NamedQuery(
        name = "loadmessagebyid",
        query = "SELECT m FROM Message m WHERE m.id = :id"
)
@NamedEntityGraphs(
    @NamedEntityGraph(
            name = "messagewithtopic",
            attributeNodes = {
            @NamedAttributeNode(value = "topic")
            }
    ))
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private RegisteredUser sender;

    private String text;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime sendingTime;

    private boolean isDeleted;

    @ManyToOne
    private Topic topic;

    private boolean isCommented;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    List<Message> comments;

    @ManyToOne
    private Message parent;
    
    
    public Message(RegisteredUser sender, String text, LocalDateTime sendingTime) {
        this.sender = sender;
        this.text = text;
        this.sendingTime = sendingTime;
        this.isDeleted = false;
    }

    public Message() {
        this.sendingTime = LocalDateTime.now();
        this.isDeleted = false;
        this.isCommented = false;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getText() {
        return text;
    }

    public RegisteredUser getSender() {
        return sender;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public int getId() {
        return id;
    }

    public List<Message> getComments() {
        return comments;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(RegisteredUser sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }

    public void setToDelete() {
        this.isDeleted = true;
    }

    public void setNotToDelete() {
        this.isDeleted = false;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isIsCommented() {
        return isCommented;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setIsCommented(boolean isCommented) {
        this.isCommented = isCommented;
    }

    public void setComments(List<Message> comments) {
        this.comments = comments;
    }

    public Message getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }

    @JsonIgnore
    public int getNumberOfComments() {
        return this.comments.size();
    }
    
    

}
