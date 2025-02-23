package by.urbans.springproject.dao;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.bean.UserRecipeOperation;
import by.urbans.springproject.enums.RecipeOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Repository
public class RecipeDAOImpl implements RecipeDAO {

    private final SessionFactory sessionFactory;
    private final UserRecipeOperationDAO userRecipeOperationDAO;

    @Autowired
    public RecipeDAOImpl(SessionFactory sessionFactory, UserRecipeOperationDAO userRecipeOperationDAO) {
        this.sessionFactory = sessionFactory;
        this.userRecipeOperationDAO = userRecipeOperationDAO;
    }

    @Override
    public Recipe getRecipe(int recipeId) {
        if (recipeId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Recipe.class, recipeId);
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Recipe> query = currentSession.createQuery("from Recipe", Recipe.class);
        return new HashSet<>(query.getResultList());
    }

    @Override
    public boolean createOrUpdateRecipe(Recipe recipe, User currentUser) {
        if (recipe == null) {
            return false;
        }
        boolean isRecipeNew;

        Session currentSession = sessionFactory.getCurrentSession();

        // Временно создаем пользователя, если он не передан
        if (currentUser == null) {
            currentUser = new User("x", "123");
        } else {
            currentUser = currentSession.merge(currentUser);
        }

        Recipe existingRecipe = currentSession.get(Recipe.class, recipe.getId());

        try {
            if (existingRecipe == null) {
                // Создаем новый рецепт
                recipe.setAuthorSet(new HashSet<>());
                recipe.getAuthorSet().add(currentUser);
                isRecipeNew = true;
                currentSession.persist(recipe);

            } else {
                // Обновляем существующий рецепт
                existingRecipe.setName(recipe.getName());
                existingRecipe.setDescription(recipe.getDescription());
                existingRecipe.setCaloricValue(recipe.getCaloricValue());
                existingRecipe.setMealCategory(recipe.getMealCategory());
                existingRecipe.getAuthorSet().add(currentUser);
                existingRecipe.setIngredients(recipe.getIngredients());
                isRecipeNew = false;
                currentSession.merge(existingRecipe);
            }

            currentSession.flush();

            RecipeOperation recipeOperation = isRecipeNew ? RecipeOperation.ADDED : RecipeOperation.EDITED;
            UserRecipeOperation userRecipeOperation = new UserRecipeOperation(currentUser, recipe, recipeOperation, LocalDateTime.now());
            userRecipeOperationDAO.createUserRecipeOperation(userRecipeOperation);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteRecipe(int recipeId, User currentUser) {
        if (recipeId <= 0) {
            return false;
        }
        Session currentSession = sessionFactory.getCurrentSession();

        Recipe recipeToRemove = getRecipe(recipeId);
        if (recipeToRemove == null) {
            return false;
        }

        try {
            UserRecipeOperation userRecipeOperation = new UserRecipeOperation(currentUser, recipeToRemove, RecipeOperation.EDITED, LocalDateTime.now());

            userRecipeOperationDAO.createUserRecipeOperation(userRecipeOperation);

            currentSession.remove(recipeToRemove);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
