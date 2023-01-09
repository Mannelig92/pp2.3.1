package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
//@RequestMapping("/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String mainPage(){

        return "mainPage";
    }

    @GetMapping(value = "/allUsers")
    public String showAllUsers(Model model) { //����� ���� ������
//        if (userService.getAllUsers().isEmpty()) {
//            userService.saveUser(new User("Harry", "Potter", "@potter.com"));
//            userService.saveUser(new User("Ron", "Wisley", "@Wisley.com"));
//            userService.saveUser(new User("Hermione", "Granger", "@Granger.com"));
//        }
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "allUsers";
    }

    //    � {id} ����� ��������� ����� ����� � ��� ���������� � �������� PathVariable
    @GetMapping(value = "/{id}/edit") //���� �� ��������
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute(userService.getUser(id));
        //������� �������� �� id �� Dao � ��������� �� ����������� � �������������
        return "edit";
    }

    @GetMapping(value = "/newUser")
    public String saveNewUser(Model model) { //���������� ������ �����
//        User user = new User();
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/";
    }
//    @GetMapping("/saveUser") //������� �� �������� �������� ����� ���������� ������������
//    public String saveUser(Model model) {
//        model.addAttribute("user", new User());
//
//        return "redirect:/people";
//    }

}
