package by.urbans.springproject.dao.Impl;

import by.urbans.springproject.bean.UserRecipeOperation;
import by.urbans.springproject.dao.UserRecipeOperationDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRecipeOperationDAOImpl implements UserRecipeOperationDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRecipeOperationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public List<UserRecipeOperation> getAllUserRecipeOperations(int userId) {
        if (userId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserRecipeOperation> query = currentSession.createQuery("from UserRecipeOperation where recipeAuthor.id = :userId", UserRecipeOperation.class);
        query.setParameter("userId", userId);
        try {
            return new ArrayList<>(query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при отображении операций пользователя", e);
        }
    }

    @Transactional
    @Override
    public UserRecipeOperation getUserRecipeOperationById(int userRecipeOperationId) {
        if (userRecipeOperationId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(UserRecipeOperation.class, userRecipeOperationId);
    }

    @Transactional
    @Override
    public boolean createUserRecipeOperation(UserRecipeOperation userRecipeOperation) {
        if (userRecipeOperation == null) {
            return false;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.merge(userRecipeOperation);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании операции", e);
        }
    }

    @Transactional
    @Override
    public boolean deleteUserRecipeOperation(int userRecipeOperationId) {
        if (userRecipeOperationId <= 0) {
            return false;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        UserRecipeOperation userRecipeOperationToDelete = getUserRecipeOperationById(userRecipeOperationId);

        if (userRecipeOperationToDelete == null) {
            return false;
        }

        try {
            currentSession.remove(userRecipeOperationToDelete);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при удалении операции", e);
        }
    }

    @Transactional
    @Override
    public UserRecipeOperation findUserRecipeOperationByUserId(int currentUserId) {
        if (currentUserId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();
        Query<UserRecipeOperation> query = currentSession.createQuery("from UserRecipeOperation where recipeAuthor.id = :userId", UserRecipeOperation.class);
        query.setParameter("userId", currentUserId);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске операции", e);
        }
    }

    @Transactional
    @Override
    public UserRecipeOperation findUserRecipeOperationByRecipeId(int recipeId) {
        if (recipeId <= 0) {
            return null;
        }

        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserRecipeOperation> query = currentSession.createQuery("from UserRecipeOperation where recipe.id = :recipeId", UserRecipeOperation.class);
        query.setParameter("recipeId", recipeId);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске операции", e);
        }
    }
}
