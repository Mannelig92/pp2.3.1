package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Component
public class UserDaoImpl implements UserDao { //Dao для соединения с БД
    // Аннотация @PersistenceContext предназаначена для автоматического связывания менеджера сущностей с бином.
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user); //Метод persist добавляет entity который мы передаём
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        entityManager.remove(entityManager.find(User.class, id)); //Удаляет из БД по id
    }

    @Override
    public List<User> getAllUsers() {
        //Свой диалект JPQL. u это users
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void editUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }
}
