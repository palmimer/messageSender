/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.service.MessageServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author progmatic
 */

@Controller
public class MessageController {
    
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);
    
    private MessageServiceImpl messageService;
    private UserStatisticsService userStatistics;

    @Autowired
    public MessageController(MessageServiceImpl msimpl, UserStatisticsService ustat ) {
        this.messageService = msimpl;
        this.userStatistics = ustat;
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String listMessages(
            @RequestParam(value = "mc", defaultValue = "15") int messageCountToShow,
            @RequestParam(value = "order", defaultValue = "false") boolean inOrder,
            Model model){
        
        List<Message> shortList = messageService.listMessages(messageCountToShow, inOrder);
        model.addAttribute("messages", shortList);
        return "messages";
    }
    
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public String showSingleMessage(
            @PathVariable("messageId") int messageId, Model model){
        
        Message singleMessageToShow = messageService.getSingleMessage(messageId);
        model.addAttribute("message", singleMessageToShow);
        return "singlemessage";
    }
    
    @RequestMapping(value = "/messages/writenew", method = RequestMethod.GET)
    public String writeNewMessage(Model model){
        model.addAttribute("message", new Message(userStatistics.getUser(), "", LocalDateTime.now()));
        return "newmessageform";
    }
    
    @RequestMapping(value = "/messages/newmessage", method = RequestMethod.POST)
    public String createNewMessage(
            @Valid
            @ModelAttribute("message") Message message, BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            return "newmessageform";
        }
        userStatistics.setUser(message.getSender());
        messageService.addNewMessage(message);
        userStatistics.addNewMessage(message);
        return "redirect:/messages";
    }
    
    @RequestMapping(value = "/messages/statistics", method = RequestMethod.GET)
    public String sendStatistics(Model model){
        // az utoljára küldő felhasználó neve
        model.addAttribute("sender", userStatistics.getUser());
        // a vizsgált felhasználó új üzeneteinek száma
        model.addAttribute("countOfNewMessagesOfUser", userStatistics.countMessagesOfUser(userStatistics.getUser())) ;
        // felhasználónevek a munkamenetben
        model.addAttribute("usernames", userStatistics.getUserNamesInSession());
        // felhasználónevek száma a munkamenetben
        model.addAttribute("countOfUsers", userStatistics.countUsersInSession());
        // az összes üzenet száma a munkamenetben
        model.addAttribute("countOfAllMessages", userStatistics.countMessagesInSession());
        userStatistics.logMessageStatisticsInSession();
        return "statisticspage";
    }
    
}