/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

/**
 *
 * @author progmatic
 */
public class TopicResponseDTO {
    
    private int id;
    
    private String name;
    
    private String title;

    public TopicResponseDTO() {
    }

    public TopicResponseDTO(int id, String name, String title) {
        this.id = id;
        this.name = name;
        this.title = title;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
}
