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

public class UserStatistics {
    private String user;
    private int countOfNewMessages;

    public UserStatistics(String user, int countOfNewMessages) {
        this.user = user;
        this.countOfNewMessages = countOfNewMessages;
    }

    public String getUser() {
        return user;
    }

    public int getCountOfNewMessages() {
        return countOfNewMessages;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCountOfNewMessages(int countOfNewMessages) {
        this.countOfNewMessages = countOfNewMessages;
    }
    
    
}
