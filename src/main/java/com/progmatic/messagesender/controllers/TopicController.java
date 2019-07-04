/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.Topic;
import com.progmatic.messagesender.service.TopicServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
public class TopicController {
    
    private TopicServiceImpl topicService;

    public TopicController(TopicServiceImpl topicService) {
        this.topicService = topicService;
    }
    
    @RequestMapping(value = "/messages/newtopicform", method = RequestMethod.GET)
    public String writeNewMessage(Model model){
        model.addAttribute("topic", new Topic("", ""));
        return "newtopicform";
    }
    
    @RequestMapping(value = "/messages/newtopic", method = RequestMethod.POST)
    public String createNewTopic(
            @Valid
            @ModelAttribute("topic") Topic topic, BindingResult bindingResult ){
        
        if (bindingResult.hasErrors()) {
            return "newtopicform";
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        topic.setSender(user.getUsername());
        topicService.addNewTopic(topic);
        return "redirect:/messages/writenew";
    }
    
    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public String listMessages( Model model){
        model.addAttribute("topics", topicService.getAllTopics());
        return "topics";
    }
    
    @RequestMapping(value = "/topics/{topic.id}", method = RequestMethod.GET)
    public String showSingleMessage(
            @PathVariable("topic.id") int topicId, 
            Model model){
        Topic topic = topicService.getTopicWithMessages(topicId);
        
        model.addAttribute("topic", topic);
        return "messagesoftopic";
    }
}
