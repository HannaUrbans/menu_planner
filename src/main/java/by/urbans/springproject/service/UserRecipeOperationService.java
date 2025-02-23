package by.urbans.springproject.service;

import by.urbans.springproject.bean.UserRecipeOperation;

import java.util.List;

public interface UserRecipeOperationService {

    List<UserRecipeOperation> getAllUserRecipeOperations(int userId);

    UserRecipeOperation getUserRecipeOperationById(int userRecipeOperationId);

    boolean createUserRecipeOperation(UserRecipeOperation userRecipeOperation);

    boolean deleteUserRecipeOperation(int userRecipeOperationId);

    UserRecipeOperation findUserRecipeOperationByUserId(int currentUserId);

    UserRecipeOperation findUserRecipeOperationByRecipeId(int recipeId);
}
