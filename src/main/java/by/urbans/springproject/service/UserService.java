package by.urbans.springproject.service;

import by.urbans.springproject.bean.Auth;
import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.Role;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.enums.MealCategory;

import java.util.List;

public interface UserService {
    User doAuth(Auth auth);

    boolean doReg(User user);

    List<Role> getRoleList();
}
