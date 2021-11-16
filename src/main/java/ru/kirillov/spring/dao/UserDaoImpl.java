package ru.kirillov.spring.dao;

import org.springframework.stereotype.Repository;
import ru.kirillov.spring.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext                      // используем вместо @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(int id, User updatedUser) {
        User userToBeUpdated = getUser(id);
        userToBeUpdated.setId(updatedUser.getId());
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setSurname(updatedUser.getSurname());
        userToBeUpdated.setSalary(updatedUser.getSalary());
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUser(id));
    }
}
