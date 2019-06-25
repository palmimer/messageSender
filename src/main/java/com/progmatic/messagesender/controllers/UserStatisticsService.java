/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.UserStatistics;
import com.progmatic.messagesender.service.MessageServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author progmatic
 */

@Component
@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserStatisticsService {
    private String user;
    private List<Message> newMessagesInSession;
    
    
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private MessageServiceImpl messageServiceImpl;

    public UserStatisticsService() {
        this.newMessagesInSession = new ArrayList<>();
    }

    public String getUser() {
        try {
            return user;
        } catch (NullPointerException ex){
            return "Még nincs egy felhasználó sem a munkamenetben.";
        }
    }

    public void setUser(String sender) {
        this.user = sender;
    }
    
    public int countMessagesInSession(){
        return newMessagesInSession.size();
    }
    
    public int countMessagesOfUser(String user){
        return (int) newMessagesInSession.stream()
                .filter(message -> message.getSender().equals(user))
                .count();
    }
    
    public List<String> getUserNamesInSession(){
        return newMessagesInSession.stream()
                .map(message -> message.getSender())
                .distinct()
                .collect(Collectors.toList());
    }
    
    public int countUsersInSession(){
        return getUserNamesInSession().size();
    }
    
    public void addNewMessage(Message m){
        newMessagesInSession.add(m);
    }
    
    public void logMessageStatisticsInSession(){
        String countOfNewMessages = String.valueOf(countMessagesInSession());
        String countOfUsers = String.valueOf(countUsersInSession());
        List<String> names = getUserNamesInSession();
        logger.info(countOfNewMessages, countOfUsers, names ) ;
    }
    
    
}
