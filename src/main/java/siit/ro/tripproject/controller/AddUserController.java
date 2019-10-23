package siit.ro.tripproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import siit.ro.tripproject.model.User;
import siit.ro.tripproject.persistence.UserRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


//This is the controller that creates or edits users

@Controller
public class AddUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder pwdEncoder;

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public ModelAndView addUser() {
        ModelAndView model = new ModelAndView("addUser");
        User user = new User();
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute User user,
                           BindingResult bindingResult) {
        user.setPassword(pwdEncoder.encode(user.getPassword()));        //we encrypt the password before we save it in DB

        // We check to see if the username is already taken with a custom method defined at the bottom
        if (userNameExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username already exists");
        }
        // If there are validation errors we refresh the page and tell were and what the problems are
        if (bindingResult.hasErrors()) {
            return "addUser";
        }

        User savedUser = userRepository.save(user);

        return "redirect:/tripPage";
    }


    @RequestMapping(value = "/editUser")
    public ModelAndView editUser() {
        ModelAndView model = new ModelAndView("/editUser");
        User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User editedUser = userRepository.findById(looged.getId()).get();
        model.addObject("user", editedUser);  //We get the currently logged user and we added to the view to populate the fields

        return model;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public String editUser( @ModelAttribute User user,BindingResult bindingResult) {
        User looged = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setPassword(looged.getPassword());

        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        User savedUser = userRepository.save(user);


        return "redirect:/tripPage";
    }

 // Here we created a method that checks if the selected username already exists

    private boolean userNameExists(String username) {
        boolean exists = false;
        List<User> list = (ArrayList) userRepository.findAll();
        for (User user : list) {
            if (user.getUsername().equals(username)) {
                exists = true;
                break;
            }

        }
        return exists;
    }
}
