/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.SearchCriteriaDTO;
import com.progmatic.messagesender.repository.MessageRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author progmatic
 */

@Service
public class MessageServiceImpl {
    
    private MessageRepository messageRepository;
    
    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        
        this.messageRepository = messageRepository;
        
    }
    
    public List<Message> filterMessages(SearchCriteriaDTO searchCriteria) {
        
        return messageRepository.filterMessages(searchCriteria);
    }
    
    public Message getSingleMessage(int messageId){
        return messageRepository.getOne(messageId);
    }
    
    
    public int getNumberOfComments(Message message){
        return message.getComments().size();
    }
    
    public List<Message> getCommentsOfMessage(int messageId){
        return messageRepository.getCommentsOfMessage(messageId);
    }
    
    
    public void addNewMessage(Message message) {
        messageRepository.save(message);
    }
    
    public void setMessageAsCommented(Message message) {
        messageRepository.setMessageAsCommented(message);
    }
    
    @Transactional
    public void createNewComment(int parentMessageId, Message comment){
        Message messageToUpdate = messageRepository.getOne(parentMessageId);
        setMessageAsCommented(messageToUpdate);
        
        RegisteredUser user = (RegisteredUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setSender(user);
        comment.setTopic(messageToUpdate.getTopic());
        comment.setParent(messageToUpdate);
        addNewMessage(comment);
    }
    
    public void setMessageToDelete(int messageId){
        messageRepository.setMessageToDelete(messageId);
    }
    
    public void restoreMessage(int messageId){
        messageRepository.restoreMessage(messageId);
    }
    
    public void finallyDeleteSelectedMessages(){
        messageRepository.finallyDeleteSelectedMessages();
    }
    
//    public List<Message> listNotDeletedMessages(int messageCountToShow, boolean inOrder, int topicId, String sender, String text){
//        
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, 
//                topicId, sender, text, getNotDeletedMessages());
//        return processedList;
//    }
//    
//    @Transactional
//    public List<Message> listAllMessages(int messageCountToShow, boolean inOrder, int topicId, String sender, String text){
//        
//        List<Message> messages = getMessages();
//        
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, topicId, sender, text, messages);
//        return processedList;
//    }
//    
//    public List<Message> listDeletedMessages(int messageCountToShow, 
//            boolean inOrder, int topicId, String sender, String text){
//        List<Message> deletedMessages = getDeletedMessages();
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, topicId, sender, text, deletedMessages);
//        return processedList;
//    }
//    
//    public List<Message> getMessages() {
//        return em.createQuery("SELECT m FROM Message m").getResultList();
//    }
//    
//    private List<Message> processListByConditions(int messageCountToShow, 
//            boolean inOrder, int topicId, String sender, String text, List<Message> list){
//        if (topicId > 0) {
//            list = filterMessagesByTopic(topicId);
//        }
//        
//        list = filterMessages(sender, text);
//        
//        if(messageCountToShow < 0) {
//            messageCountToShow = list.size();
//        }
//        List<Message> processedList;
//        if (inOrder) {
//            processedList = putListInOrderByName(list);
//        } else {
//            processedList = list;
//        }
//        List<Message> shortList = makeShortList(messageCountToShow, processedList);
//        return shortList;
//    }
    
//    @Transactional
//    public List<Message> getNotDeletedMessages(){
//        return em.createQuery("SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
//                .setParameter("isDeleted", false)
//                .getResultList();
//        
////        List<Message> validMessages = new ArrayList<>();
////        for (int i = 0; i < messages.size(); i++) {
////            if (!messages.get(i).isIsDeleted()) {
////                validMessages.add(messages.get(i));
////            }
////        }
////        return validMessages;
//    }
//    
//    
//    @Transactional
//    public void addNewMessage(Message message) {
//        em.persist(message);
////        messages.add(message);
//    }
//    
//    
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @Transactional
//    private void deleteMessage(int messageId) {
//        
//        em.remove(getSingleMessage(messageId));
////        int index = findIndexOfMessage(messageId);
////        if (index > -1 ) {
////            messages.remove(index);
////        }
//    }
    
//    private int findIndexOfMessage(int messageId){
//        int indexToFind = -1;
//        for (int i = 0; i < messages.size(); i++) {
//            if (messages.get(i).getId() == messageId) {
//                indexToFind = i;
//            }
//        }
//        return indexToFind;
//    }
    
//    private Map<Integer, Message> makeMapWithIds() {
//        Map<Integer, Message> mapWithIds = new HashMap<>();
//        for (Message message : messages) {
//            mapWithIds.put(message.getId(), message);
//        }
//        return mapWithIds;
//    }
    
//    private List<Message> putListInOrderByName(List<Message> list){
//        return list.stream().sorted(new SenderComparator()).collect(Collectors.toList());
//    }
//    
//    private List<Message> makeShortList(int messageCountToShow, List<Message> list ){
//        // Math.min() amelyik a kisebb érték, azt adjuk meg a sublist végső értékének
//        return list.subList(0, Math.min(list.size(), messageCountToShow));
//    }
//    
//    @Transactional
//    private List<Message> getDeletedMessages() {
//        return em.createQuery("SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
//                .setParameter("isDeleted", true)
//                .getResultList();
////        List<Message> deletedMessages = new ArrayList<>();
////        for (int i = 0; i < messages.size(); i++) {
////            if (messages.get(i).isIsDeleted()) {
////                deletedMessages.add(messages.get(i));
////            }
////        }
////        return deletedMessages;
//    }
//    
//    @Transactional
//    private List<Message> filterMessagesByTopic(int topicId) {
//        return em.createQuery("SELECT m FROM Message m WHERE m.topic.id = :topicId")
//                .setParameter("topicId", topicId)
//                .getResultList();
//    }
//    
    

    
    
}
