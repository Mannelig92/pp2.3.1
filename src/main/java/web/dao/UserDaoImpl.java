package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Component
//@Transactional //Всё тело метода будет выполняться в одной транзакции БД
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
        entityManager.remove(id); //Удаляет из БД по id
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
//        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u where u.id =:id", User.class);
//        q.setParameter("id", id); //Передаём параметр id по которому будем доставать пользователя из бд
//        //Должен быть один элемент, если его нет, то возвращаем null
//        return q.getResultList().stream().findAny().orElse(null);
        return entityManager.find(User.class, id);
    }
}
