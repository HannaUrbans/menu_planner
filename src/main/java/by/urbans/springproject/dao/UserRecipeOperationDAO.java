package by.urbans.springproject.dao;

import by.urbans.springproject.bean.UserRecipeOperation;

import java.util.List;
import java.util.Set;

public interface UserRecipeOperationDAO {
    List<UserRecipeOperation> getAllUserRecipeOperations();

    UserRecipeOperation getUserRecipeOperationById(int userRecipeOperationId);

    boolean createUserRecipeOperation(UserRecipeOperation userRecipeOperation);

    boolean deleteUserRecipeOperation(int userRecipeOperationId);

    UserRecipeOperation findUserRecipeOperationByUserId (int currentUserId);

    UserRecipeOperation findUserRecipeOperationByRecipeId (int recipeId);
}
