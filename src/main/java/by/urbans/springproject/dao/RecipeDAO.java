package by.urbans.springproject.dao;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.enums.MealCategory;

import java.util.List;
import java.util.Set;

public interface RecipeDAO {

    Recipe getRecipe(int recipeId) throws DAOException;

    List<Recipe> getAllRecipes() throws DAOException;

    boolean createOrUpdateRecipe(Recipe recipe, User currentUser) throws DAOException;

    boolean deleteRecipe(int recipeId, User currentUser) throws DAOException;

    List<Recipe> getRecipesByCategory(MealCategory category) throws DAOException;

}
