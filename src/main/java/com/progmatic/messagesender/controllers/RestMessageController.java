/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.MessageDTO;
import com.progmatic.messagesender.MessageResponseDTO;
import com.progmatic.messagesender.MessageResponseDTOList;
import static com.progmatic.messagesender.Message_.topic;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.SearchCriteriaDTO;
import com.progmatic.messagesender.Topic;
import com.progmatic.messagesender.TopicResponseDTO;
import com.progmatic.messagesender.service.MessageServiceImpl;
import com.progmatic.messagesender.service.TopicServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author progmatic
 */
@RestController
public class RestMessageController {
    
    MessageServiceImpl messageService;
    TopicServiceImpl topicService;
    
    @Autowired
    public RestMessageController(MessageServiceImpl messageService, TopicServiceImpl topicService ) {
        this.messageService = messageService;
        this.topicService = topicService;
    }
    
    @GetMapping(value = "/api/messages/{messageId}")
    public MessageResponseDTO singleMessage( @PathVariable("messageId") int messageId ){
        Message singleMessage = messageService.getSingleMessage(messageId);
        Topic topic = singleMessage.getTopic();
        MessageResponseDTO response = 
                new MessageResponseDTO(
                        singleMessage.getId(), 
                        singleMessage.getText(),
                        singleMessage.getSender().getUsername(),
                        singleMessage.getSendingTime(),
                        new TopicResponseDTO(topic.getId(), topic.getSender(), topic.getTitle())
                );
        return response;
    }
    
    
    @GetMapping(value = "/api/messages", produces = MediaType.APPLICATION_XML_VALUE)
    public MessageResponseDTOList listMessagesXml(
            @RequestParam(value = "messageCount", defaultValue = "15") int messageCount,
            @RequestParam(value = "inorder", defaultValue = "false") boolean inOrder,
            @RequestParam(value = "selectMessages", defaultValue = "notdeleted") String selectMessages,
            @RequestParam(value = "topicChoice", defaultValue= "0" ) int topicId,
            @RequestParam(value = "sender", defaultValue="") String sender,
            @RequestParam(value = "text", defaultValue="") String text){
        
        SearchCriteriaDTO criteria = new SearchCriteriaDTO(messageCount, inOrder, selectMessages, topicId, sender, text);
        List<Message> messages = messageService.filterMessages(criteria);
        
        List<MessageResponseDTO> responses = new ArrayList<>();
        for (Message message : messages) {
            Topic topic = message.getTopic();
            MessageResponseDTO response = new MessageResponseDTO(
                    message.getId(), 
                    message.getText(),
                    message.getSender().getUsername(),
                    message.getSendingTime(),
                    new TopicResponseDTO(topic.getId(), topic.getSender(), topic.getTitle())
            );
            responses.add(response);
        }
        MessageResponseDTOList messageResponseDTOs = new MessageResponseDTOList(responses);
        return messageResponseDTOs;
    }
    
    @GetMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageResponseDTO> listMessagesJson(
            @RequestParam(value = "messageCount", defaultValue = "15") int messageCount,
            @RequestParam(value = "inorder", defaultValue = "false") boolean inOrder,
            @RequestParam(value = "selectMessages", defaultValue = "notdeleted") String selectMessages,
            @RequestParam(value = "topicChoice", defaultValue= "0" ) int topicId,
            @RequestParam(value = "sender", defaultValue="") String sender,
            @RequestParam(value = "text", defaultValue="") String text){
        
        SearchCriteriaDTO criteria = new SearchCriteriaDTO(messageCount, inOrder, selectMessages, topicId, sender, text);
        List<Message> messages = messageService.filterMessages(criteria);
        
        List<MessageResponseDTO> responses = new ArrayList<>();
        for (Message message : messages) {
            Topic topic = message.getTopic();
            MessageResponseDTO response = new MessageResponseDTO(
                    message.getId(), 
                    message.getText(),
                    message.getSender().getUsername(),
                    message.getSendingTime(),
                    new TopicResponseDTO(topic.getId(), topic.getSender(), topic.getTitle())
            );
            responses.add(response);
        }
        return responses;
    }
    
    @PostMapping(value = "/api/messages")
    public MessageResponseDTO createMessage(@RequestBody MessageDTO mdto){
        
        RegisteredUser user = (RegisteredUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Message newMessage = new Message(user, mdto.getText(), LocalDateTime.now());
        Topic topic = topicService.getTopicById(mdto.getTopicId());
        newMessage.setTopic(topic);
        newMessage.setSender(user);
        messageService.addNewMessage(newMessage);
        
        MessageResponseDTO newMessageResponse = new MessageResponseDTO(
                    newMessage.getId(), 
                    newMessage.getText(),
                    newMessage.getSender().getUsername(),
                    newMessage.getSendingTime(),
                    new TopicResponseDTO(topic.getId(), topic.getSender(), topic.getTitle())
        );
        return newMessageResponse;
    }
    
    @RequestMapping(value = "/api/messages/{messageId}", method = RequestMethod.DELETE)
    public void deleteMessage( @PathVariable("messageId") int messageId ){
        Message messageToDelete = messageService.getSingleMessage(messageId);
        messageService.finallyDeleteMessage(messageToDelete);
    }
    
    
}
