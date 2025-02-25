package by.urbans.springproject.dao.Impl;

import by.urbans.springproject.bean.Role;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.dao.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// спринг security!
@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserById(int userId) {
        if (userId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(User.class, userId);
    }

    @Override
    public User getUserByLogin(String login) {
        if (login == null) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery("FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);

        return query.uniqueResult();
    }

    @Override
    public String getHashedPasswordByLogin(String login) {
        if (login == null) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        Query<String> query = currentSession.createQuery("SELECT u.hashedPassword FROM User u WHERE u.login = :login", String.class);
        query.setParameter("login", login);

        return query.uniqueResult();
    }

    @Override
    public Set<User> getAllUsers() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> query = currentSession.createQuery("from User", User.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean createOrUpdateUser(User user) {
        if (user == null) {
            return false;

        }

        Session currentSession = sessionFactory.getCurrentSession();
        try {
            currentSession.merge(user);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании/удалении пользователя", e);
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        if (userId <= 0) {
            return false;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        User userToDelete = getUserById(userId);
        if (userToDelete == null) {
            return false;
        }

        try {
            currentSession.remove(userToDelete);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }

    }

    @Override
    public List<Role> getRoleList() {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("from Role", Role.class).getResultList();
    }
}
