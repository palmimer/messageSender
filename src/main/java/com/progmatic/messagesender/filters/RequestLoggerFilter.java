/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.filters;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 *
 * @author progmatic
 */

@Component
public class RequestLoggerFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestLoggerFilter.class);
    
    @Override
    public void doFilter(ServletRequest srq, ServletResponse srp, FilterChain fc) 
            throws IOException, ServletException {
//        Map<String, String[]> incoming = srq.getParameterMap();
//        for (Map.Entry<String, String[]> entry : incoming.entrySet()) {
//            System.out.println(entry.getKey() + ": ");
//            for (String string : entry.getValue() ) {
//                System.out.println(string);
//            }
//        }
        
        srq.getParameterMap().forEach((k, v) -> logger.debug("{}: {}", k , v));
        
//        srq.getParameterMap().forEach((k, v) -> System.out.println(k + ": " + String.join(",", v)));
        
//        System.out.println("Bejövő: Az új üzenet feladója: " + srq.getParameter("sender"));
//        System.out.println("Bejövő: Az új üzenet szövege: " + srq.getParameter("text"));
        
        
        fc.doFilter(srq, srp);
        
    }

    
}
