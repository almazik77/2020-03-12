package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.SignUpService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ConfirmController {

    @Autowired
    private SignUpService signUpService;

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/confirm/{confirm-code:.+}", method = RequestMethod.GET)
    public ModelAndView confirmEmail(@PathVariable("confirm-code") String confirmCode, HttpServletResponse response) {
        signUpService.confirm(confirmCode);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success_confirm_page");
        return modelAndView;
    }
}
