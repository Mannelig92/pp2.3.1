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
    public String showAllUsers(Model model) { //вывод всех юзеров
//            userService.saveUser(new User("Harry", "Potter", "@potter.com"));
        model.addAttribute("allUsers", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping(value = "/newUser")
    public String saveNewUser(@ModelAttribute("user") User user) { //Добавление нового юзера
        return "newUser";
    }

    @PostMapping()
    public String save(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/allUsers";
    }
    @GetMapping
    public String showUser(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getUser(id));
        return "showUser";
    }

    //получение юзера по id. вместо id можно будет поместить число и с помощью аннотации PathVariable
    //мы извлёчём этот id из url и получим к нему доступ
    @GetMapping(value = "/edit/{id}") //Пока не работает
    public String editUser(@PathVariable("id") int id, Model model) {
        //Получаем текущего человека по его id
        model.addAttribute("editUser",userService.getUser(id));
        return "edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user){
        userService.editUser(user);
        return "redirect:/allUsers";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        userService.removeUserById(id);
        return "redirect:/allUsers";
    }
}
