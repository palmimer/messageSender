/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;

/**
 *
 * @author progmatic
 */
@Entity
@NamedEntityGraphs(
    @NamedEntityGraph(
            name = "topicsWithMessages",
            attributeNodes = {
            @NamedAttributeNode(value = "messages")
            }
    ))
public class Topic implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String sender;
    
    @Column(unique = true)
    private String title;
    
    @OneToMany(cascade=REMOVE, mappedBy="topic")
    private List<Message> messages;

    public Topic(String sender, String title) {
        this.sender = sender;
        this.title = title;
    }

    public Topic(){
        
    }
    
    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getTitle() {
        return title;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
    
}
