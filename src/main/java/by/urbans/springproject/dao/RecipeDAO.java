package by.urbans.springproject.dao;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;

import java.util.Set;

public interface RecipeDAO {

    Recipe getRecipe(int recipeId);

    Set<Recipe> getAllRecipes();

    boolean createOrUpdateRecipe(Recipe recipe, User currentUser);

    boolean deleteRecipe(int recipeId);

}
