package by.urbans.springproject.service.Impl;

import by.urbans.springproject.bean.Auth;
import by.urbans.springproject.bean.Role;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.dao.UserDAO;
import by.urbans.springproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public User doAuth(Auth auth) {
        if (!checkAuth(auth)) {
            return null;
        }

        return userDAO.getUserByLogin(auth.getLogin());
    }

    private boolean checkAuth(Auth auth) {
        String password = auth.getPassword();
        String hashedPassword = userDAO.getHashedPasswordByLogin(auth.getLogin());

        if (hashedPassword == null) {
            return false;
        }

        return new BCryptPasswordEncoder().matches(password, hashedPassword);
    }

    @Transactional
    @Override
    public boolean doReg(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }

        if (!userDAO.createOrUpdateUser(user)) {
            throw new RuntimeException("Не удалось создать или обновить пользователя");
        }

        return true;
    }

    @Transactional
    @Override
    public List<Role> getRoleList() {
        return userDAO.getRoleList();
    }
}
