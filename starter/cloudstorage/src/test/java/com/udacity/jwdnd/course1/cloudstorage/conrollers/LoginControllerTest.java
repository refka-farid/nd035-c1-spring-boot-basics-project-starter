package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
class LoginControllerTest {

    @Inject
    WebApplicationContext context;

    @Inject
    private UserMapper mapper;

    @AfterEach
    void tearDown() {
        mapper.deleteAll();
    }

    private static MockMvc mockMvc;

    @BeforeEach
    public void initMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void check_loginView_GET() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("<h1 class=\"display-5\">Login</h1>")));
    }

    @Test
    void check_loginUser_POST_when_success() throws Exception {
        mapper.addUser(new User(null,"leticia","HIxi7PbCRU9uIyET6sdGEg==","8H7jlDi3a2iPiu9ZI1+krA==","leticia","leticia"));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "leticia")
                .param("password", "azerty"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"))
        ;
    }

    @Test
    void check_loginUser_POST_when_error() throws Exception {
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "Aliii")
                .param("password", "AliIII"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
        ;
    }
}
