package by.urbans.springproject.dao;

import by.urbans.springproject.bean.Role;
import by.urbans.springproject.bean.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {

    User getUserById(int userId);

    String getHashedPasswordByLogin(String login);

    User getUserByLogin(String login);

    Set<User> getAllUsers();

    boolean createOrUpdateUser(User user);

    boolean deleteUser(int userId);

    List<Role> getRoleList();
}
