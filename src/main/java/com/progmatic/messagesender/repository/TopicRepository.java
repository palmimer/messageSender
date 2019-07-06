/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Topic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author progmatic
 */
public interface TopicRepository extends JpaRepository<Topic, Integer>, CustomTopicRepository {
    
    Topic findByTitle(String title);
    
    boolean existsByTitle(String title);
    
    boolean existsById(int id);
    
    
    
}
