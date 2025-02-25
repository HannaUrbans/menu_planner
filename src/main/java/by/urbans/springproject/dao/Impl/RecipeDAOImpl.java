package by.urbans.springproject.dao.Impl;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.bean.UserRecipeOperation;
import by.urbans.springproject.dao.DAOException;
import by.urbans.springproject.dao.RecipeDAO;
import by.urbans.springproject.dao.UserRecipeOperationDAO;
import by.urbans.springproject.enums.MealCategory;
import by.urbans.springproject.enums.RecipeOperation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;


@Repository
public class RecipeDAOImpl implements RecipeDAO {

    private final SessionFactory sessionFactory;
    private final UserRecipeOperationDAO userRecipeOperationDAO;

    private static final String GET_ALL_RECIPES =
            "from Recipe r " +
            "where r.id in (select u.recipe.id from UserRecipeOperation u) " +
            "order by (select max(u.operationTimestamp) from UserRecipeOperation u where u.recipe.id = r.id) desc";
    private static final String GET_ALL_RECIPES_BY_CATEGORY = "from Recipe where mealCategory = :mealCategory";

    @Autowired
    public RecipeDAOImpl(SessionFactory sessionFactory, UserRecipeOperationDAO userRecipeOperationDAO) {
        this.sessionFactory = sessionFactory;
        this.userRecipeOperationDAO = userRecipeOperationDAO;
    }


    @Override
    public Recipe getRecipe(int recipeId) {
        if (recipeId <= 0) {
            throw new DAOException("Неверный ID рецепта: " + recipeId);
        }

        try {
            Session currentSession = sessionFactory.getCurrentSession();
            return currentSession.get(Recipe.class, recipeId);
        } catch (HibernateException e) {
            throw new DAOException("Ошибка с получением рецепта с ID " + recipeId, e);
        }
    }

    @Override
    public List<Recipe> getAllRecipes() {
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            Query<Recipe> query = currentSession.createQuery(GET_ALL_RECIPES, Recipe.class);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new DAOException("Ошибка с получением всех рецептов", e);
        }
    }

    @Override
    public boolean createOrUpdateRecipe(Recipe recipe, User currentUser) {
        if (recipe == null) {
            throw new DAOException("Рецепт не может быть null");
        }

        if (currentUser == null) {
            throw new DAOException("User не может быть null");
        }

        boolean isRecipeNew;

        try {
            Session currentSession = sessionFactory.getCurrentSession();

            // подгружаем объект сущности User, который загружается ленивой загрузкой
            if (!currentSession.contains(currentUser)) {
                currentUser = currentSession.get(User.class, currentUser.getId());
            }

            // ищем в бд рецепт
            Recipe existingRecipe = currentSession.get(Recipe.class, recipe.getId());

            //не нашли? создаем новый + добавляем автора в таблицу
            if (existingRecipe == null) {
                recipe.setAuthorSet(new HashSet<>());
                recipe.getAuthorSet().add(currentUser);
                isRecipeNew = true;
                currentSession.persist(recipe);

            } else {
                // нашли? обновляем
                // копируем свойства из одного бина в другой кроме поля authorSet (ВМЕСТО СЕТТЕРОВ)
                BeanUtils.copyProperties(recipe, existingRecipe, "authorSet");
                existingRecipe.getAuthorSet().add(currentUser);
                isRecipeNew = false;

                // можно делать merge не recipe, a existingRecipe, потому что хибер уже отслеживает existingRecipe, зная, что он связан с recipe
                currentSession.merge(existingRecipe);
            }

            // принудительно синхронизируем с бд перед добавлением данных в таблицу с историей действий пользователя
            currentSession.flush();

            // добавляем инфу в таблицу с историей действий пользователя
            RecipeOperation recipeOperation = isRecipeNew ? RecipeOperation.ADDED : RecipeOperation.EDITED;
            UserRecipeOperation userRecipeOperation = new UserRecipeOperation(currentUser, recipe, recipeOperation, LocalDateTime.now());
            userRecipeOperationDAO.createUserRecipeOperation(userRecipeOperation);

            return true;
        } catch (HibernateException e) {
            throw new DAOException("Ошибка при добавлении/изменении рецепта", e);
        }
    }

    @Override
    public boolean deleteRecipe(int recipeId, User currentUser) {
        if (recipeId <= 0) {
            throw new DAOException("Неверный ID рецепта: " + recipeId);
        }

        if (currentUser == null) {
            throw new DAOException("User не может быть null");
        }

        try {
            Session currentSession = sessionFactory.getCurrentSession();

            Recipe recipeToRemove = getRecipe(recipeId);
            if (recipeToRemove == null) {
                throw new DAOException("Рецепт не может быть null");
            }

            // продумать механизм удаления!!!
            // или оставить boolen isDeleted, или убрать отчет о том, что рецепт удален
            UserRecipeOperation userRecipeOperation = new UserRecipeOperation(currentUser, recipeToRemove, RecipeOperation.DELETED, LocalDateTime.now());
            userRecipeOperationDAO.createUserRecipeOperation(userRecipeOperation);

            currentSession.remove(recipeToRemove);

            return true;
        } catch (HibernateException e) {
            throw new DAOException("Ошибка при удалении рецепта", e);
        }
    }

    public List<Recipe> getRecipesByCategory(MealCategory category) {
        if (category == null) {
            throw new DAOException("Категория не может быть null");
        }

        try {
            Session currentSession = sessionFactory.getCurrentSession();

            Query<Recipe> query = currentSession.createQuery(GET_ALL_RECIPES_BY_CATEGORY, Recipe.class);
            query.setParameter("mealCategory", category);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new DAOException("Ошибка при получении рецептов по категории", e);
        }
    }
}
