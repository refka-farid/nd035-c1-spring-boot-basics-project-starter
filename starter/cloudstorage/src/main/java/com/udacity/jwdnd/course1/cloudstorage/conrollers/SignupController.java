package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.UserUiDto;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    // TODO: 30/05/2021 to be tested

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute UserUiDto userUiDto, Model model) {
        String signupError = null;

        if (!userService.isUsernameAvailable(userUiDto.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(userUiDto.toUser());
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }
// TODO: 30/05/2021 should encapsulate model response
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
