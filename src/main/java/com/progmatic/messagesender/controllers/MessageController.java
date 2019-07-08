/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.service.UserStatisticsService;
import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.Topic;
import com.progmatic.messagesender.service.MessageServiceImpl;
import com.progmatic.messagesender.service.TopicServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
    private TopicServiceImpl topicService;
    private UserStatisticsService userStatistics;

    @Autowired
    public MessageController(MessageServiceImpl msimpl, TopicServiceImpl tsimpl, UserStatisticsService ustat ) {
        this.messageService = msimpl;
        this.topicService = tsimpl;
        this.userStatistics = ustat;
    }
    
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String listMessages(
            @RequestParam(value = "mc", defaultValue = "15") int messageCountToShow,
            @RequestParam(value = "order", defaultValue = "false") boolean inOrder,
            @RequestParam(value = "selectMessages", defaultValue = "notdeleted") String selectMessages,
            @RequestParam(value = "topicChoice", defaultValue= "0" ) int topicId,
            HttpServletRequest servletRequest,
            Model model){
        List<Message> shortList = new ArrayList<>();
        if (servletRequest.isUserInRole("ADMIN")) {
            
            if (selectMessages.equals("deleted")) {
                shortList = messageService.listDeletedMessages(messageCountToShow, inOrder, topicId);
            } else if (selectMessages.equals("all")) {
                shortList = messageService.listAllMessages(messageCountToShow, inOrder, topicId);
            } else {
                shortList = messageService.listNotDeletedMessages(messageCountToShow, inOrder, topicId);
            }
            
        } else if (servletRequest.isUserInRole("USER")){
            
            shortList = messageService.listNotDeletedMessages(messageCountToShow, inOrder, topicId);
        }
        model.addAttribute("messages", shortList);
        model.addAttribute("selectMessages", selectMessages);
        model.addAttribute("topics", topicService.getAllTopics());
        model.addAttribute("topicChoice", topicId);
        return "messages";
    }
    
    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public String showSingleMessage(
            @PathVariable("messageId") int messageId, Model model){
        
        Message singleMessageToShow = messageService.getSingleMessage(messageId);
        model.addAttribute("message", singleMessageToShow);
        model.addAttribute("comments", singleMessageToShow.getComments());
        return "singlemessage";
    }
    
    @RequestMapping(value = "/messages/writenew", method = RequestMethod.GET)
    public String writeNewMessage(Model model){
        model.addAttribute("message", new Message());
        List<Topic> allTopics = topicService.getAllTopics();
        model.addAttribute("topics", allTopics );
        return "newmessageform";
    }
    
    @RequestMapping(value = "/messages/newmessage", method = RequestMethod.POST)
    public String createNewMessage(
            @Valid
            @ModelAttribute("message") Message message, 
            BindingResult bindingResult ){
        
        if (bindingResult.hasErrors()) {
            return "newmessageform";
        }
        
        RegisteredUser user = (RegisteredUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        message.setSender(user);
        //message.setTopic(topic);
        messageService.addNewMessage(message);
        
        userStatistics.setUser(user.getUsername());
        userStatistics.addNewMessage(message);
        return "redirect:/messages";
    }
    
    @RequestMapping(value = "/messages/{message.id}/writecomment", method = RequestMethod.GET)
    public String writeComment(
            @PathVariable("message.id") int messageId,
            Model model){
        Message message = messageService.getSingleMessage(messageId);
        model.addAttribute("message", message);
        model.addAttribute("comment", new Message());
        return "newcommentform";
    }
    
    
    @RequestMapping(value = "/messages/{message.id}/newcomment", method = RequestMethod.POST)
    public String createNewComment(
            @PathVariable("message.id") int messageId,
            @ModelAttribute("comment") Message comment,
            BindingResult bindingResult ){
        
        if (bindingResult.hasErrors()) {
            return "newcommentform";
        }
        messageService.createNewComment(messageId, comment);
        
        RegisteredUser user = (RegisteredUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userStatistics.setUser(user.getUsername());
        userStatistics.addNewMessage(comment);
        return "redirect:/messages/" + messageId;
    }
    
    @RequestMapping(value= "/messages/delete/{messageId}", method = RequestMethod.GET)
    public String deleteMessage(
            @PathVariable("messageId") int messageId){
        messageService.setMessageToDelete(messageId);
        return "redirect:/messages";
    }
    
    @RequestMapping(value= "/messages/restore/{messageId}", method = RequestMethod.GET)
    public String restoreMessage(
            @PathVariable("messageId") int messageId){
        messageService.restoreMessage(messageId);
        return "redirect:/messages";
    }
    
    @GetMapping("messages/finaldelete")
    public String finallyDeleteSelectedMessages(HttpServletRequest servletRequest){
        if (servletRequest.isUserInRole("ADMIN")) {
            messageService.finallyDeleteSelectedMessages();
        }
        return "redirect:/messages";
    }
    
    @RequestMapping(value = "/messages/statistics", method = RequestMethod.GET)
    public String sendStatistics(Model model){
        // az utoljára küldő felhasználó neve
        model.addAttribute("sender", userStatistics.getUser());
        // a vizsgált felhasználó új üzeneteinek száma
        model.addAttribute("countOfNewMessagesOfUser", userStatistics.countMessagesOfUser(userStatistics.getUser())) ;
        // felhasználónevek a munkamenetben
        model.addAttribute("usernames", userStatistics.getUsersInSession());
        // felhasználónevek száma a munkamenetben
        model.addAttribute("countOfUsers", userStatistics.countUsersInSession());
        // az összes üzenet száma a munkamenetben
        model.addAttribute("countOfAllMessages", userStatistics.countMessagesInSession());
        userStatistics.logMessageStatisticsInSession();
        return "statisticspage";
    }
    
    private boolean hasRole(String role) {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(role)) {
                return true;
            }
        }
        return false;
    }
    
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<String> handleErrors(EntityNotFoundException ex){
//        ResponseEntity<String> ret = new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        return ret;
//    }
}
