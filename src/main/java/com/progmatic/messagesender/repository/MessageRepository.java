/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author progmatic
 */


public interface MessageRepository extends JpaRepository<Message, Integer>, CustomMessageRepository {
    
    Message findBySender(String username);
    
    boolean existsBySender(String username);
    
    boolean existsById(int id);
    
}
