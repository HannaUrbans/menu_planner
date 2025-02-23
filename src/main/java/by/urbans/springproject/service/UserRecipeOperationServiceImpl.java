package by.urbans.springproject.service;

import by.urbans.springproject.bean.UserRecipeOperation;
import by.urbans.springproject.dao.UserRecipeOperationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRecipeOperationServiceImpl implements UserRecipeOperationService {

    private final UserRecipeOperationDAO userRecipeOperationDAO;

    @Autowired
    public UserRecipeOperationServiceImpl(UserRecipeOperationDAO userRecipeOperationDAO) {
        this.userRecipeOperationDAO = userRecipeOperationDAO;
    }

    @Transactional
    @Override
    public List<UserRecipeOperation> getAllUserRecipeOperations() {
        return new ArrayList<>(userRecipeOperationDAO.getAllUserRecipeOperations());
    }

    @Transactional
    @Override
    public UserRecipeOperation getUserRecipeOperationById(int userRecipeOperationId) {
        if (userRecipeOperationId <= 0){
            return null;
        }

        return userRecipeOperationDAO.getUserRecipeOperationById(userRecipeOperationId);
    }

    @Transactional
    @Override
    public boolean createUserRecipeOperation(UserRecipeOperation userRecipeOperation) {
        if (userRecipeOperation == null){
            return false;
        }

        return userRecipeOperationDAO.createUserRecipeOperation(userRecipeOperation);
    }

    @Transactional
    @Override
    public boolean deleteUserRecipeOperation(int userRecipeOperationId) {
        if (userRecipeOperationId <= 0){
            return false;
        }

        return userRecipeOperationDAO.deleteUserRecipeOperation(userRecipeOperationId);
    }

    @Transactional
    @Override
    public UserRecipeOperation findUserRecipeOperationByUserId(int currentUserId) {
        if (currentUserId <= 0){
            return null;
        }

        return userRecipeOperationDAO.findUserRecipeOperationByUserId(currentUserId);
    }

    @Transactional
    @Override
    public UserRecipeOperation findUserRecipeOperationByRecipeId(int recipeId) {
        if (recipeId <= 0){
            return null;
        }

        return userRecipeOperationDAO.findUserRecipeOperationByRecipeId(recipeId);
    }
}
