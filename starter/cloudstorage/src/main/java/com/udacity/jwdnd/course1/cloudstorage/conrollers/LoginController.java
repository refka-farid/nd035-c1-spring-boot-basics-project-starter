package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    // TODO: 30/05/2021 to be tested

    @GetMapping()
    public String loginView() {
        return "login";
    }
}
