package by.urbans.springproject.service.Impl;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.dao.DAOException;
import by.urbans.springproject.dao.RecipeDAO;
import by.urbans.springproject.enums.MealCategory;
import by.urbans.springproject.service.RecipeService;
import by.urbans.springproject.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeDAO recipeDAO;

    @Autowired
    public RecipeServiceImpl(RecipeDAO recipeDAO) {
        this.recipeDAO = recipeDAO;
    }

    @Transactional
    @Override
    public Recipe getRecipe(int recipeId) {
        try {
            return recipeDAO.getRecipe(recipeId);
        } catch (DAOException e) {
            throw new ServiceException("Произошла ошибка при получении рецепта", e);
        }
    }

    @Transactional
    @Override
    public List<Recipe> getAllRecipes() {
        try {
            return recipeDAO.getAllRecipes();
        } catch (DAOException e) {
            throw new ServiceException("Произошла ошибка при получении списка рецептов", e);
        }
    }

    @Transactional
    @Override
    public boolean createOrUpdateRecipe(Recipe recipe, User currentUser) {
        try {
            return recipeDAO.createOrUpdateRecipe(recipe, currentUser);
        } catch (DAOException e) {
            throw new ServiceException("Произошла ошибка при создании/обновлении рецепта", e);
        }
    }

    @Transactional
    @Override
    public boolean deleteRecipe(int recipeId, User currentUser) {
        try {
            return recipeDAO.deleteRecipe(recipeId, currentUser);
        } catch (DAOException e) {
            throw new ServiceException("Произошла ошибка при удалении рецепта", e);
        }
    }

    @Transactional
    @Override
    public List<Recipe> getRecipeByCategory(MealCategory category) {
        try {
            return recipeDAO.getRecipesByCategory(category);
        } catch (DAOException e) {
            throw new ServiceException("Произошла ошибка при получении рецептов одной категории", e);
        }
    }
}
