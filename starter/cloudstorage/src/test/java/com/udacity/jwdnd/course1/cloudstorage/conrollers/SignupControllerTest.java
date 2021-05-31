package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.SignupResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//@WebAppConfiguration
//@ContextConfiguration
class SignupControllerTest {

    @Inject
    private WebApplicationContext context;

    private MockMvc mvcMock;

    @MockBean
    private UserService userServiceMock;

    @BeforeEach
    public void setup() {
        mvcMock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void signupViewGetTest() throws Exception {
        mvcMock.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("responseModel", is(new SignupResponseDto())))
                .andExpect(view().name("signup"))
                .andExpect(content().string(containsString("<h1 class=\"display-5\">Sign Up</h1>")))
        ;
    }

    @Test
    void verify_signupUser_should_showError_When_userAlreadyExist() throws Exception {
        given(userServiceMock.isUsernameAvailable("Ali")).willReturn(false);

        SignupResponseDto expected = new SignupResponseDto();
        expected.setErrorSignup("The username already exists.");

        mvcMock.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Ali")
                .param("lastName", "Ali")
                .param("userName", "Ali")
                .param("password", "Ali"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("responseModel", is(expected)))
        ;
    }

    @Test
    void verify_signupUser_should_showError_When_userIsNotCreated() throws Exception {
        given(userServiceMock.isUsernameAvailable(anyString())).willReturn(true);
        given(userServiceMock.createUser(any())).willReturn(false);

        SignupResponseDto expected = new SignupResponseDto();
        expected.setErrorSignup("There was an error signing you up. Please try again.");

        mvcMock.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Ali")
                .param("lastName", "Ali")
                .param("userName", "Ali")
                .param("password", "Ali"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("responseModel", is(expected)))
        ;
    }

    @Test
    void verify_signupUser_should_showSuccess_When_userIsNotCreated() throws Exception {
        given(userServiceMock.isUsernameAvailable(anyString())).willReturn(true);
        given(userServiceMock.createUser(any())).willReturn(true);

        SignupResponseDto expected = new SignupResponseDto();
        expected.setSuccessSignup(true);

        mvcMock.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "peter")
                .param("lastName", "peter")
                .param("userName", "peter")
                .param("password", "peter"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("responseModel", is(expected)))
        ;
    }

}