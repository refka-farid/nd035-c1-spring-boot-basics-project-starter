package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.SignupResponseModel;
import com.udacity.jwdnd.course1.cloudstorage.models.UserUiDto;
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
    // TODO: 30/05/2021 to be tested

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(Model model) {
        model.addAttribute("responseModel", new SignupResponseModel());
        return "signup";
    }

    @PostMapping()
    public ModelAndView signupUser(@ModelAttribute UserUiDto userUiDto) {

        String signupError = null;

        if (!userService.isUsernameAvailable(userUiDto.getUserName())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            boolean rowsAdded = userService.createUser(userUiDto.toUser());
            if (!rowsAdded) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }
// TODO: 30/05/2021 should encapsulate model response
        SignupResponseModel responseModel = new SignupResponseModel();
        if (signupError == null) {
//            model.addAttribute("signupSuccess", true);
            responseModel.setSuccessSignup(true);
        } else {
            responseModel.setErrorSignup(signupError);
//            model.addAttribute("signupError", signupError);
        }

//        return "signup";
        return new ModelAndView("signup", "responseModel", responseModel);
    }
}
