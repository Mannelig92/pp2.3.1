package web.controller;

import web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    @GetMapping(value = "/")
    public String users(Model model) {
        List<User> listOfUsers = new ArrayList<>();
        Collections.addAll(listOfUsers,
                new User("Harry", "Potter", 22),
                new User("Ron", "Wisley", 23),
                new User("Hermione", "Granger", 21),
                new User("Nevill", "Longbottom", 22),
                new User("Tom", "Reddle", 54));
        model.addAttribute("listOfUsers", listOfUsers);
        return "user";
    }
}
