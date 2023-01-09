package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping("/people")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showAllUsers(Model model) { //вывод всех юзеров
        if (userService.getAllUsers().isEmpty()) {
            userService.saveUser(new User("Harry", "Potter", 22));
            userService.saveUser(new User("Ron", "Wisley", 23));
            userService.saveUser(new User("Hermione", "Granger", 21));
        }
//        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", userService.getAllUsers());
        return "people/allUsers";
    }

    //    ¬ {id} можно поместить любое число и оно поместитс€ в аргумент PathVariable
    @GetMapping(value = "/{id}/edit")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute(userService.getUser(id));
        //ѕолучим человека по id из Dao и передадим на отображение в представление
        return "people/edit";
    }

    @GetMapping(value = "/newUser")
    public String saveNewUser(Model model) { //ƒобавление нового юзера
//        User user = new User();
        model.addAttribute("user", new User());
        return "people/newUser";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/people";
    }
//    @GetMapping("/saveUser") //переход на основную страницу после добавлени€ пользовател€
//    public String saveUser(Model model) {
//        model.addAttribute("user", new User());
//
//        return "redirect:/people";
//    }

}
