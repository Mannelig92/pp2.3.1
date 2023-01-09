package web.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public void saveUser(User user) {
        entityManager.persist(user); //����� persist ��������� entity ������� �� �������
    }

    @Override
    public void removeUserById(long id) {
        entityManager.remove(id); //������� �� �� �� id
    }

    @Override
    public List<User> getAllUsers() {
        //���� ������� JPQL. u ��� users
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public User getUser(long id) {
        TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u where u.id =:id", User.class);
        q.setParameter("id", id); //������� �������� id �� �������� ����� ��������� ������������ �� ��
        //������ ���� ���� �������, ���� ��� ���, �� ���������� null
        return q.getResultList().stream().findAny().orElse(null);
    }
}
