/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.repository;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.SearchCriteriaDTO;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author progmatic
 */

@Repository
public interface CustomMessageRepository {
    
    
    List<Message> filterMessages(SearchCriteriaDTO searchCriteria);
    
//    List<Message> listNotDeletedMessages(int messageCountToShow, boolean inOrder, int topicId, String sender, String text);
    
//    List<Message> listAllMessages(
//            int messageCountToShow, boolean inOrder, int topicId, String sender, String text);
    
//    List<Message> listDeletedMessages(int messageCountToShow, 
//            boolean inOrder, int topicId, String sender, String text);
    
//    List<Message> getMessages();
    
//    List<Message> processListByConditions(int messageCountToShow, 
//            boolean inOrder, int topicId, String sender, String text, List<Message> list);
    
//    List<Message> getNotDeletedMessages();
    
    Message getSingleMessage(int messageId);
    
    void addNewMessage(Message message);
    
    void setMessageAsCommented(Message message);
    
    void createNewComment(int parentMessageId, Message comment);
    
    public void setMessageToDelete(int messageId);
    
    void restoreMessage(int messageId);
    
    void finallyDeleteSelectedMessages();
    
    void deleteMessage(int messageId);
    
    List<Message> putListInOrderByName(List<Message> list);
    
    List<Message> makeShortList(int messageCountToShow, List<Message> list );
    
    List<Message> getDeletedMessages();
    
    List<Message> filterMessagesByTopic(int topicId);
    
    int getNumberOfComments(Message message);
    
    List<Message> getCommentsOfMessage(int messageId);
    
    
}
