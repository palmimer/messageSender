/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.util.Comparator;

/**
 *
 * @author progmatic
 */
public class SenderComparator implements Comparator<Message> {

    @Override
    public int compare(Message o1, Message o2) {
        return o1.getSender().getUsername().compareTo(o2.getSender().getUsername());
    }
    
}
