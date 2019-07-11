/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author progmatic
 */

@XmlRootElement(name = "messages")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageResponseDTOList {
    
    @XmlElement(name = "message")
    List<MessageResponseDTO> messageDTOs = new ArrayList<>();

    public MessageResponseDTOList(List<MessageResponseDTO> messageDTOs) {
        this.messageDTOs = messageDTOs;
    }

    public List<MessageResponseDTO> getMessageDTOs() {
        return messageDTOs;
    }

    public void setMessageDTOs(List<MessageResponseDTO> messageDTOs) {
        this.messageDTOs = messageDTOs;
    }
    
    
}
