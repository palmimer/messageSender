/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.SenderComparator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author progmatic
 */

@Service

public class MessageServiceImpl {
    
    private List<Message> messages;
    
    @PersistenceContext
    EntityManager em;
    
    public MessageServiceImpl() {
        
//        this.messages = new ArrayList<>();
        

//        Message m1 = new Message("Lacika", "Hívd fel légyszi anyut!", LocalDateTime.now());
//        Message m2 = new Message("Géza", "Meg kellene beszélni valamit, hívj fel!", LocalDateTime.now());
//        Message m3 = new Message("Anyu", "Hogy ment a meghallgatás?", LocalDateTime.now());
//        Message m4 = new Message("Lajos", "Vigyél esernyőt!", LocalDateTime.now());
//        Message m5 = new Message("Anyu", "Megtaláltam az esőkabátodat.", LocalDateTime.now());
//        Message m6 = new Message("Lilla", "Akkor jössz ma este?", LocalDateTime.now());
//        Message m7 = new Message("Lajos", "Holnap jó idő lesz, találkozunk?", LocalDateTime.now());
//        Message m8 = new Message("Kata", "Jó, akkor talizunk 4-kor a szökőkútnál.", LocalDateTime.now());
//        messages.add(m1);
//        messages.add(m2);
//        messages.add(m3);
//        messages.add(m4);
//        messages.add(m5);
//        messages.add(m6);
//        messages.add(m7);
//        messages.add(m8);
    }
    
    public List<Message> listNotDeletedMessages(int messageCountToShow, boolean inOrder){
        
        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, getNotDeletedMessages());
        return processedList;
    }
    
    @Transactional
    public List<Message> listAllMessages(int messageCountToShow, boolean inOrder){
        
        List<Message> messages = getMessages();
        
        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, messages);
        return processedList;
    }
    
    public List<Message> listDeletedMessages(int messageCountToShow, boolean inOrder){
        List<Message> deletedMessages = getDeletedMessages();
        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, deletedMessages);
        return processedList;
    }
    
    public List<Message> getMessages() {
        return em.createQuery("SELECT m FROM Message m").getResultList();
    }
    
    private List<Message> processListByConditions(int messageCountToShow, boolean inOrder, List<Message> list){
        if(messageCountToShow < 0) {
            messageCountToShow = list.size();
        }
        List<Message> processedList;
        if (inOrder) {
            processedList = putListInOrderByName(list);
        } else {
            processedList = list;
        }
        List<Message> shortList = makeShortList(messageCountToShow, processedList);
        return shortList;
    }
    
    @Transactional
    private List<Message> getNotDeletedMessages(){
        return em.createQuery("SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
                .setParameter("isDeleted", false)
                .getResultList();
        
//        List<Message> validMessages = new ArrayList<>();
//        for (int i = 0; i < messages.size(); i++) {
//            if (!messages.get(i).isIsDeleted()) {
//                validMessages.add(messages.get(i));
//            }
//        }
//        return validMessages;
    }
    @Transactional
    public Message getSingleMessage(int messageId){
//        Map<Integer, Message> messagesWithIds = makeMapWithIds();
//        return messagesWithIds.get(messageId);
        return em.find(Message.class, messageId);
                
//                .createQuery("SELECT m FROM Message m WHERE m.id = :id")
//                .setParameter("id", messageId)
//                .getSingleResult();
        
    }
    
    @Transactional
    public void addNewMessage(Message message) {
        em.persist(message);
//        messages.add(message);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void deleteMessage(int messageId) {
        
        em.remove(getSingleMessage(messageId));
//        int index = findIndexOfMessage(messageId);
//        if (index > -1 ) {
//            messages.remove(index);
//        }
    }
    
    @Transactional
    public void setMessageToDelete(int messageId){
        getSingleMessage(messageId).setToDelete();
    }
    
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
    
    private List<Message> putListInOrderByName(List<Message> list){
        return list.stream().sorted(new SenderComparator()).collect(Collectors.toList());
    }
    
    private List<Message> makeShortList(int messageCountToShow, List<Message> list ){
        // Math.min() amelyik a kisebb érték, azt adjuk meg a sublist végső értékének
        return list.subList(0, Math.min(list.size(), messageCountToShow));
    }

    private List<Message> getDeletedMessages() {
        return em.createQuery("SELECT m FROM Message m WHERE m.isDeleted = :isDeleted")
                .setParameter("isDeleted", true)
                .getResultList();
//        List<Message> deletedMessages = new ArrayList<>();
//        for (int i = 0; i < messages.size(); i++) {
//            if (messages.get(i).isIsDeleted()) {
//                deletedMessages.add(messages.get(i));
//            }
//        }
//        return deletedMessages;
    }
    
}
