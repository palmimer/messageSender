/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Topic;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author progmatic
 */
@Repository
public interface CustomTopicRepository {
    
//    List<Topic> getAllTopics();
    
//    void addNewTopic(Topic topic);
    
//    Topic getTopicById(int topicId);
    
    Topic getTopicWithMessages(int topicId);
    
//    void saveTopic(Topic topic);
    
//    boolean topicExists(String topicName);
    
}
