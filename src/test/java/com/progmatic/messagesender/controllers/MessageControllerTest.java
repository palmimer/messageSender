/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.messagesender.controllers;

import com.progmatic.messagesender.Message;
import com.progmatic.messagesender.service.MessageServiceImpl;
import com.progmatic.messagesender.service.TopicServiceImpl;
import com.progmatic.messagesender.service.UserStatisticsService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 *
 * @author progmatic
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(listeners = WithSecurityContextTestExecutionListener.class)
public class MessageControllerTest {
    
    MockMvc mockMvc;
    MessageServiceImpl messageService;
    TopicServiceImpl topicService;
    @Mock UserStatisticsService userStatistics;
    
    public MessageControllerTest() {
    }
    
    @Before
    public void setUp() {
        // tesztkörnyezet létrehozása
        MockitoAnnotations.initMocks(this); // behozza a Mock-kal kapcsolatos annotációkat(nem feltétlenül szükséges)
        
        // egy hamis messageService és userStatistics osztály létrehozása a teszteléshez
        messageService = Mockito.mock(MessageServiceImpl.class);
        topicService = Mockito.mock(TopicServiceImpl.class);
//        userStatistics = Mockito.mock(UserStatisticsService.class);

        mockMvc = MockMvcBuilders.standaloneSetup(new MessageController(messageService, topicService, userStatistics))
                .setViewResolvers(new InternalResourceViewResolver("", ".html"))
                .build();
        
        // tesztüzenetek létrehozása
        List<Message> messagesToReturn = new ArrayList<>();
        messagesToReturn.add(new Message("Ctesztküldő3", "Ctesztüzenet3", LocalDateTime.now()));
        messagesToReturn.add(new Message("Atesztküldő1", "Atesztüzenet1", LocalDateTime.now()));
        messagesToReturn.add(new Message("Btesztküldő2", "Btesztüzenet2", LocalDateTime.now()));
        
        // megmondom, ha meghívja az adott metódust, mivel térjen vissza
        // a paraméterek beadása helyett írhatom egyenként: Mockito.anyInt(), stb
        Mockito.when(messageService.listAllMessages(Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(messagesToReturn);
        Mockito.when(messageService.listNotDeletedMessages(Mockito.anyInt(), Mockito.anyBoolean()))
                .thenReturn(messagesToReturn);
        Mockito.when(messageService.getSingleMessage(Mockito.anyInt()))
                .thenReturn(messagesToReturn.get(0));
    }

    @Test
    @WithMockUser(roles = "USER", username = "Merci")
    public void testListMessages() throws Exception {
        // ha elküldünk egy get request-et a messages oldalra, visszakapjuk-e a kért oldalt (nevét)
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages"))
                .andExpect(MockMvcResultMatchers.view().name("messages"));
        // a view resolver ugyanazt a két szót látja messages-t, ezért meg kell adni egy view resolvert
        // a mockMvc-nek (fönt), hogy a két szót máshogy értelmezze
    }
    
    @Test
    public void testListMessages2() throws Exception {
        // a requestre kapott válaszban lesz egy status kód, azt nézzük meg
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    @WithMockUser(roles = "USER", username = "Merci")
    public void testListMessages3() throws Exception {
        // azt várjuk el, hogy a modelben legyenek bizonyos attribútumok
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/messages"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("messages")).andReturn();
        
        List<Message> returnedMessages = (List<Message>) result.getModelAndView()
                .getModel().get("messages");
        
        assertEquals(3, returnedMessages.size());
    }
    
    @Test
    @WithMockUser(roles = "USER", username = "Merci")
    public void testListMessages4() throws Exception {
        // 
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages"))
                .andExpect(MockMvcResultMatchers.model().attribute("messages", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.model().attribute("messages", Matchers.hasItem(
                    Matchers.allOf(
                            Matchers.hasProperty("sender", Matchers.is("Atesztküldő1")),
                            Matchers.hasProperty("text", Matchers.is("Atesztüzenet1"))
                    )
                )));
    }
    
    @Test
    public void testShowSingleMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages/1"))
                .andExpect(MockMvcResultMatchers.view().name("singlemessage"));
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages/1"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", 
                        Matchers.allOf(
                        Matchers.hasProperty( "sender", Matchers.is("Ctesztküldő3") ),
                        Matchers.hasProperty( "text", Matchers.is("Ctesztüzenet3") )
                )));
        
    }
    
    @Test
    public void testWriteNewMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/messages/writenew"))
                .andExpect(MockMvcResultMatchers.view().name("newmessageform"));
    }

    @Test
    public void testCreateNewMessage() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                .post("/messages/newmessage"));
        
//                .param("text", "Ez egy tesztüzenet");
        
    }

    @Test
    public void testDeleteMessage() {
    }

    @Test
    public void testSendStatistics() {
    }

    @Test
    public void testHandleErrors() {
    }
    
}
