package ru.itis.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignUpDto;
import ru.itis.services.SignUpService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;


    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView getSignUpPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign-up");
        return modelAndView;
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public void signUp(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        SignUpDto form = SignUpDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        signUpService.signUp(form);
        response.sendRedirect("/signIn");
    }

}

