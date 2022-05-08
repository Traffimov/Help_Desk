package com.training.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.dto.AuthRequest;
import com.training.model.User;
import com.training.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    public static final String LOGIN = "manager1_mogilev@yopmail.com";
    public static final String PASSWORD = "P@ssword1";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    UserController userController;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    void createAuthenticationTokenTest() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin(LOGIN);
        authRequest.setPassword(PASSWORD);

        ObjectMapper objectMapper = new ObjectMapper();
        String test = null;
        try {
            test = objectMapper.writeValueAsString(authRequest);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/users/authenticate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(test))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        User user = userService.findByLogin(LOGIN);
        Assertions.assertNotNull(user);
    }
}