package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {


    void saveUser(User user); //Добавление юзера

    void removeUserById(long id); //Удаление юзеров

    List<User> getAllUsers(); //Вывод всех юзеров

    void editUser(User user);

    User getUser(long id);

}
