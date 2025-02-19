package by.urbans.springproject.controller;

import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.dao.RecipeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


    @RestController
    @RequestMapping("/recipes")
    public class TestController {

        @Autowired
        private RecipeDAO recipeDAO;

        @GetMapping("/list")
        public Set<Recipe> getRecipes() {
            return recipeDAO.getAllRecipes();
        }
    }

