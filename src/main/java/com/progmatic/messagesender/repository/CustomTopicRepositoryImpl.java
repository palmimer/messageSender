/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Topic;
import java.util.List;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import static org.hibernate.jpa.QueryHints.HINT_LOADGRAPH;
import org.springframework.stereotype.Repository;

/**
 *
 * @author progmatic
 */

@Repository
public class CustomTopicRepositoryImpl implements CustomTopicRepository {
    
    @PersistenceContext
    EntityManager em;
    
//    @Transactional
//    @Override
//    public List<Topic> getAllTopics(){
//        
//        List<Topic> topics = em.createQuery("SELECT t FROM Topic t").getResultList();
//        return topics;
//    }
    
//    @Transactional
//    @Override
//    public void addNewTopic(Topic topic){
//        em.persist(topic);
//    }
    
//    @Transactional
//    @Override
//    public Topic getTopicById(int topicId){
//        return em.find(Topic.class, topicId);
//    }
    
    @Override
    public Topic getTopicWithMessages(int topicId){
        
        EntityGraph eg = em.getEntityGraph("topicsWithMessages");
        return em.createQuery("SELECT t FROM Topic t WHERE t.id = :id", Topic.class)
                .setParameter("id", topicId)
                .setHint(HINT_LOADGRAPH, eg)
                .getSingleResult();
        
    }
    
//    public Topic getTopicByName(){
//        return new Topic();
//    }
    
//    @Override
//    public void saveTopic(Topic topic){
//        em.persist(topic);
//    }
    
    
    // ezt még meg kell írni!!
//    @Override
//    public boolean topicExists(String topicName){
//        return false;
//    }
}
