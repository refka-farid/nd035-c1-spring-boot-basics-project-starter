package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.SignupResponseDto;
import com.udacity.jwdnd.course1.cloudstorage.models.SignupRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.services.signup.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(Model model) {
        model.addAttribute("responseModel", new SignupResponseDto());
        return "signup";
    }

    @PostMapping()
    public ModelAndView signupUser(@ModelAttribute SignupRequestDto signupRequestDto) {

        String signupError = null;

        if (!userService.isUsernameAvailable(signupRequestDto.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            boolean isUserCreated = userService.createUser(signupRequestDto.toUser());
            if (!isUserCreated) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        SignupResponseDto responseModel = new SignupResponseDto();
        if (signupError == null) {
            responseModel.setSuccessSignup(true);
        } else {
            responseModel.setErrorSignup(signupError);
        }

        return new ModelAndView("signup", "responseModel", responseModel);
    }
}
