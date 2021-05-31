package com.udacity.jwdnd.course1.cloudstorage.conrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @GetMapping()
    public String loginView() {

        LOG.trace("This is a TRACE log");
        LOG.debug("This is a DEBUG log");
        LOG.info("This is an INFO log");
        LOG.error("This is an ERROR log");
        LOG.warn( "This is an WARN log");

        return "login";
    }
}
