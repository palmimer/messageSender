/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.service;

import com.progmatic.messagesender.Authority;
import com.progmatic.messagesender.RegisteredUser;
import com.progmatic.messagesender.RegistrationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        RegisteredUser singleResult = em.createNamedQuery("loaduserbyusername", RegisteredUser.class)
                .setParameter("username", string)
                .getSingleResult();
        return singleResult;
                
    }
    
    @Transactional
    public void createUser(RegistrationDTO registration) throws AlreadyExistsException{
        if (userExists(registration.getUserName())) {
            throw new AlreadyExistsException("Ez a felhasználónév már létezik (" + registration.getUserName() + ")");
        } else {
            Authority authority = getAuthority("ROLE_USER");
            RegisteredUser newUser = new RegisteredUser(
                registration.getUserName(), passwordEncoder.encode(registration.getPassword1()), 
                registration.getEmail());
            
            List<Authority> authorities = new ArrayList<>();
            authorities.add(authority);
            newUser.setAuthorities(authorities);
            em.persist(newUser);
        }
        
    }
    
    public boolean userExists(String username){
        
        List<RegisteredUser> resultList = em.createNamedQuery("loaduserbyusername", RegisteredUser.class)
                .setParameter("username", username)
                .getResultList();
        
        if (resultList.isEmpty()) {
            return false;
        } else {
            return true;
        }
        
    }

    private Authority getAuthority(String role) {
        //query ami az authority táblából kiveszi az egész entitást
        
        return em.createQuery("SELECT a FROM Authority a WHERE a.authority = :authority", Authority.class)
                .setParameter("authority", role)
                .getSingleResult();
    }
    
    
    
}
