package siit.ro.tripproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Here we created a custom login controller with a custom view

@Controller
public class LogInController {

    @RequestMapping(value = "/")
    public ModelAndView logIn() {
        ModelAndView model = new ModelAndView("logIn");
        return model;
    }
}
