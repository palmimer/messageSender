/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Topic;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.progmatic.messagesender.repository.TopicRepository;

/**
 *
 * @author progmatic
 */
@Service
public class TopicServiceImpl {
    
    private TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository tr) {
        this.topicRepository = tr;
    }
    
    
    public List<Topic> getAllTopics(){
        
        List<Topic> topics = topicRepository.findAll();
        return topics;
    }
    
    @Transactional
    public void addNewTopic(Topic topic) throws AlreadyExistsException{
        if (topicRepository.existsByTitle(topic.getTitle())) {
            throw new AlreadyExistsException("Topic with title: " + topic.getTitle() + " already exists.");
        }
        topicRepository.save(topic);
    }
    
    
    public Topic getTopicById(int topicId){
        return topicRepository.findById(topicId).get();
    }
    
    public Topic getTopicWithMessages(int topicId){
        
        return topicRepository.getTopicWithMessages(topicId);
    }
    
    public Topic getTopicByTitle(String title){
        return topicRepository.findByTitle(title);
    }
    
   
    
    
            
}
