/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author progmatic
 */

@Entity
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String authority;
    
//    @ManyToOne
//    private List<RegisteredUser> registeredUsers;
    
    
    @Override
    public String getAuthority() {
        return authority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public List<RegisteredUser> getRegisteredUsers() {
//        return registeredUsers;
//    }
//
//    public void setRegisteredUsers(List<RegisteredUser> registeredUsers) {
//        this.registeredUsers = registeredUsers;
//    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    
    
}
