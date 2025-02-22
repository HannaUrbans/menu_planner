package by.urbans.springproject.service;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.dao.RecipeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeDAO recipeDAO;

    @Autowired
    public RecipeServiceImpl(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    @Transactional
    @Override
    public Recipe getRecipe(int recipeId) {
        return recipeDAO.getRecipe(recipeId);
    }

    @Transactional
    @Override
    public Set<Recipe> getAllRecipes() {
        return recipeDAO.getAllRecipes();
    }

    @Transactional
    @Override
    public boolean createOrUpdateRecipe(Recipe recipe, User currentUser) {
        return recipeDAO.createOrUpdateRecipe(recipe, currentUser);
    }

    @Transactional
    @Override
    public boolean deleteRecipe(int recipeId, User currentUser) {
       return recipeDAO.deleteRecipe(recipeId, currentUser);
    }
}
