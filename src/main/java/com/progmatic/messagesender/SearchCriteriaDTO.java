/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author progmatic
 */


public class SearchCriteriaDTO {
//    @RequestParam(value = "mc", defaultValue = "15") 
    private Integer messageCountToShow = 15;
    
//    @RequestParam(value = "order", defaultValue = "false") 
    private Boolean inOrder = false;
    
//    @RequestParam(value = "selectMessages", defaultValue = "notdeleted") 
    // ez három értéket vehet fel: notdeleted, deleted, v. all
    private String selectMessages = "notdeleted";
    
//    @RequestParam(value = "topicChoice", defaultValue= "0" )
    private Integer topicId = 0 ;
    
    private String sender = "";
    
    private String text = "";

    public SearchCriteriaDTO(
            Integer messageCountToShow, 
            Boolean inOrder, 
            String selectMessages, 
            Integer topicId, 
            String sender, 
            String text) {
        this.messageCountToShow = messageCountToShow;
        this.inOrder = inOrder;
        this.selectMessages = selectMessages;
        this.topicId = topicId;
        this.sender = sender;
        this.text = text;
    }

    public Integer getMessageCountToShow() {
        return messageCountToShow;
    }

    public void setMessageCountToShow(Integer messageCountToShow) {
        this.messageCountToShow = messageCountToShow;
    }

    public Boolean isInOrder() {
        return inOrder;
    }

    public void setInOrder(Boolean inOrder) {
        this.inOrder = inOrder;
    }

    public String getSelectMessages() {
        return selectMessages;
    }

    public void setSelectMessages(String selectMessages) {
        this.selectMessages = selectMessages;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
