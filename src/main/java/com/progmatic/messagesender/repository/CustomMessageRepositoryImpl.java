/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.Message_;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.RegisteredUser_;
import com.progmatic.messagesender.SearchCriteriaDTO;
import com.progmatic.messagesender.SenderComparator;
import com.progmatic.messagesender.Topic;
import com.progmatic.messagesender.Topic_;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author progmatic
 */

@Repository
public class CustomMessageRepositoryImpl implements CustomMessageRepository {
    
    @PersistenceContext
    EntityManager em;

    public CustomMessageRepositoryImpl() {
        
    }
    
//    @Transactional
//    public List<Message> listNotDeletedMessages(int messageCountToShow, boolean inOrder, int topicId, String sender, String text){
//        
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, 
//                topicId, sender, text, getNotDeletedMessages());
//        return processedList;
//    }
    
//    @Transactional
//    public List<Message> listAllMessages(int messageCountToShow, boolean inOrder, int topicId, String sender, String text){
//        
//        List<Message> messages = getMessages();
//        
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, topicId, sender, text, messages);
//        return processedList;
//    }
    
//    public List<Message> listDeletedMessages(int messageCountToShow, 
//            boolean inOrder, int topicId, String sender, String text){
//        List<Message> deletedMessages = getDeletedMessages();
//        List<Message> processedList = processListByConditions(messageCountToShow, inOrder, topicId, sender, text, deletedMessages);
//        return processedList;
//    }
    
//    public List<Message> getMessages() {
//        return em.createQuery("SELECT m FROM Message m").getResultList();
//    }
    
//    public List<Message> processListByConditions(int messageCountToShow, 
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
    
    @Transactional
    public List<Message> filterMessages(SearchCriteriaDTO searchCriteria) {
        // példányosítunk egy Criteriabuilder-t
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // meghívjuk a cb-n azt, hogy min végezze a keresést (Message)
        CriteriaQuery<Message> cQuery = cb.createQuery(Message.class);
        Root<Message> m = cQuery.from(Message.class);
        // a vizsgált adatokhoz (Message-ek) hozzáadjuk a küldőt
        Join<Message, RegisteredUser> user = m.join(Message_.sender);
        // a vizsgált adatokhoz (Message-ekhez) hozzáadjuk a topicot
        Join<Message, Topic> topic = m.join(Message_.topic);
        
        //készítek egy listát a szűrési feltételeknek
        List<Predicate> predics = new ArrayList<>();
        // szűrés a küldőre
        if (!searchCriteria.getSender().isEmpty()) {
            Predicate predic = cb.like(user.get(RegisteredUser_.username), searchCriteria.getSender());
            predics.add(predic);
        }
        // szűrés az üzenet szövege alapjén
        if (!searchCriteria.getText().isEmpty()) {
            Predicate predic = cb.like(m.get(Message_.text), "%" + searchCriteria.getText() + "%" );
            predics.add(predic);
        }
        // szűrés az üzenet topicja alapján
        if (searchCriteria.getTopicId() != 0 ) {
            Predicate predic = cb.equal(topic.get(Topic_.id), searchCriteria.getTopicId());
        }
        
        // itt átadom a szűrési feltételeket tömbbé átalakítva a cQuery-nek 
        cQuery.select(m).where(predics.toArray(new Predicate[predics.size()]));
        // futtatom a szűrést és elkérem a végeredményt
        return em.createQuery(cQuery).getResultList();
    }
    
    @Transactional
    public List<Message> getNotDeletedMessages(){
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
    
//    @Transactional
//    public Message getSingleMessage(int messageId){
////        Map<Integer, Message> messagesWithIds = makeMapWithIds();
////        return messagesWithIds.get(messageId);
//        return em
//                .createNamedQuery("loadmessagebyid", Message.class)
//                .setParameter("id", messageId)
//                .getSingleResult();
//        
//    }
    
    @Transactional
    public void addNewMessage(Message message) {
        em.persist(message);

    }
    
    @Transactional
    public void setMessageAsCommented(Message message) {
        message.setIsCommented(true);
    }
    
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void deleteMessage(int messageId) {
        
        em.remove(getSingleMessage(messageId));
    }
    
    public List<Message> putListInOrderByName(List<Message> list){
        return list.stream().sorted(new SenderComparator()).collect(Collectors.toList());
    }
    
    public List<Message> makeShortList(int messageCountToShow, List<Message> list ){
        // Math.min() amelyik a kisebb érték, azt adjuk meg a sublist végső értékének
        return list.subList(0, Math.min(list.size(), messageCountToShow));
    }
    
    @Transactional
    public List<Message> getDeletedMessages() {
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
    
    @Transactional
    public List<Message> filterMessagesByTopic(int topicId) {
        return em.createQuery("SELECT m FROM Message m WHERE m.topic.id = :topicId")
                .setParameter("topicId", topicId)
                .getResultList();
    }
    
    @Transactional
    public int getNumberOfComments(Message message){
        return message.getComments().size();
    }

    @Override
    public List<Message> getCommentsOfMessage(int messageId) {
        // példányosítunk egy Criteriabuilder-t
        CriteriaBuilder cb = em.getCriteriaBuilder();
        // meghívjuk a cb-n azt, hogy min végezze a keresést (Message)
        CriteriaQuery<Message> cQuery = cb.createQuery(Message.class);
        Root<Message> m = cQuery.from(Message.class);
        // a vizsgált adatokhoz (Message-ekhez) hozzáadjuk a kommenteket
        ListJoin<Message, Message> comments = m.join(Message_.comments);
        // szűrési feltételek megadása
        if (messageId != 0 ) {
            cQuery.select(comments).where(cb.equal(m.get(Message_.id), messageId));
        }
        // futtatom a szűrést és elkérem a végeredményt
        return em.createQuery(cQuery).getResultList();
    }
    
    
}
