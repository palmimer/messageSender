/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.RegistrationDTO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author progmatic
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    
    @PersistenceContext
    private EntityManager em;
    
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        return em.createNamedQuery("loaduserbyusername", RegisteredUser.class)
                .setParameter("username", string)
                .getSingleResult();
    }
    
    public void createUser(RegistrationDTO registration) throws AlreadyExistsException{
        if (userExists(registration.getUserName())) {
            throw new AlreadyExistsException();
        } else {
            em.persist(registration);
        }
        
    }
    
    public boolean userExists(String username){
        
        return em.contains(username);
    }
    
    
    
}
