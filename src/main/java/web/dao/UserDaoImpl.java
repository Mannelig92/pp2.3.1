package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Component
//@Transactional //�� ���� ������ ����� ����������� � ����� ���������� ��
@Repository
@Component
public class UserDaoImpl implements UserDao { //Dao ��� ���������� � ��
    // ��������� @PersistenceContext �������������� ��� ��������������� ���������� ��������� ��������� � �����.
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user); //����� persist ��������� entity ������� �� �������
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        entityManager.remove(id); //������� �� �� �� id
    }

    @Override
    public List<User> getAllUsers() {
        //���� ������� JPQL. u ��� users
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
//        q.setParameter("id", id); //������� �������� id �� �������� ����� ��������� ������������ �� ��
//        //������ ���� ���� �������, ���� ��� ���, �� ���������� null
//        return q.getResultList().stream().findAny().orElse(null);
        return entityManager.find(User.class, id);
    }
}
