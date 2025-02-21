package by.urbans.springproject.dao;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;


@Repository
public class RecipeDAOImpl implements RecipeDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public RecipeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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

        // Временно создаем пользователя, если он не передан
        if (currentUser == null) {
            currentUser = new User("x", "123");
        }

        Session currentSession = sessionFactory.getCurrentSession();
        Recipe existingRecipe = currentSession.get(Recipe.class, recipe.getId());

        try {
            if (existingRecipe == null) {
                // Создаем новый рецепт
                recipe.setAuthorSet(new HashSet<>());
                recipe.getAuthorSet().add(currentUser);
                //currentSession.merge(recipe);

            } else {
                // Обновляем существующий рецепт
                Set<User> authorSet = existingRecipe.getAuthorSet();
                if (authorSet == null) {
                    authorSet = new HashSet<>();
                    existingRecipe.setAuthorSet(authorSet);
                }
                authorSet.add(currentUser);
                recipe.setAuthorSet(authorSet);
                //currentSession.merge(recipe);
            }

            currentSession.merge(recipe);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку
            return false;
        }
    }

    @Override
    public boolean deleteRecipe(int recipeId) {
        if (recipeId <= 0) {
            return false;
        }
        Session currentSession = sessionFactory.getCurrentSession();

        Recipe recipeToRemove = getRecipe(recipeId);
        if (recipeToRemove == null) {
            return false;
        }

        try {
            currentSession.remove(recipeToRemove);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
