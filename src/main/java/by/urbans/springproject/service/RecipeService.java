package by.urbans.springproject.service;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.enums.MealCategory;

import java.util.List;

public interface RecipeService {
    Recipe getRecipe(int recipeId) throws ServiceException;

    List<Recipe> getAllRecipes() throws ServiceException;

    boolean createOrUpdateRecipe(Recipe recipe, User currentUser) throws ServiceException;

    boolean deleteRecipe(int recipeId, User currentUser) throws ServiceException;

    List<Recipe> getRecipeByCategory(MealCategory category) throws ServiceException;
}
