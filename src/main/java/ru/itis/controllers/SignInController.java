package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignInDto;
import ru.itis.services.SignInService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

    @GetMapping(value = "/signIn")
    public ModelAndView getSignInPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (request.getParameterMap().containsKey("error")) {
            modelAndView.getModel().put("error", true);
        }
        modelAndView.setViewName("sign-in");
        return modelAndView;
    }

    /*@RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public ModelAndView signIn(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        SignInDto form = SignInDto.builder()
                .email(email)
                .password(password)
                .build();

        Optional<String> sessionAttribute = signInService.signIn(form);

        if (sessionAttribute.isPresent()) {
            request.getSession().setAttribute("AUTH", sessionAttribute.get());
            response.sendRedirect("/signUp");
        } else {
            response.sendRedirect("/signIn");
        }
        return null;
    }*/

}
