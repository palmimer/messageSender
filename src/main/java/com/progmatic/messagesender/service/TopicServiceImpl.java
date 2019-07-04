/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.Topic;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author progmatic
 */
@Service
public class TopicServiceImpl {
    
    @PersistenceContext
    EntityManager em;
    
    @Transactional
    public List<Topic> getAllTopics(){
        
        List<Topic> topics = em.createQuery("SELECT t FROM Topic t").getResultList();
        return topics;
    }
    
    @Transactional
    public void addNewTopic(Topic topic){
        em.persist(topic);
    }
    
    @Transactional
    public Topic getTopicById(int topicId){
        return em.find(Topic.class, topicId);
    }
    
    public List<Message> getMessagesOfTopic(int topicId){
        return em.createQuery("SELECT t FROM Topic t JOIN FETCH T.messages WHERE t.id = :id")
                .setParameter("id", topicId)
                .getResultList();
        
    }
    
    
}
