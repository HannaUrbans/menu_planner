package by.urbans.springproject.controller;


import by.urbans.springproject.bean.Auth;
import by.urbans.springproject.bean.Recipe;
import by.urbans.springproject.bean.User;
import by.urbans.springproject.service.RecipeService;
import by.urbans.springproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("user")
public class BaseController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public BaseController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    // Главная страница
    @GetMapping("/")
    public String redirectFromIndexPage() {
        return "redirect:/goToMainPage";
    }

    @GetMapping("/goToMainPage")
    public String goToMainPage() {
        return "main-page";
    }

    // Иное
    @GetMapping("/goToStubPage")
    public String goToStubPage() {
        return "stub-page";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/goToMainPage";
    }

    // Работа с юзером АВТОРИЗАЦИЯ
    @GetMapping("/goToAuthPage")
    public String goToAuthPage(Model model) {
        model.addAttribute("auth", new Auth());
        return "auth-form";
    }

    @PostMapping("/doAuth")
    public String doAuth(@ModelAttribute("auth") Auth auth, Model model) {
        User authUser = userService.doAuth(auth);

        if (authUser != null) {
            model.addAttribute("user", authUser);
            return "redirect:/goToProfilePage";
        } else {
            return "redirect:/goToStubPage";
        }
    }

    // Работа с юзером РЕГИСТРАЦИЯ
    @GetMapping("/goToRegPage")
    public String goToRegPage(Model model) {
        List<ObjectError> errors = (List<ObjectError>) model.asMap().get("errors");
        if (errors != null) {
            model.addAttribute("errors", errors);
        }
        model.addAttribute(("user"), new User());
        return "reg-form";
    }

    @PostMapping("/doReg")
    public String doReg(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,  RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/goToRegPage";
        }

        return userService.doReg(user) ? "redirect:/goToAuthPage" : "redirect:/goToStubPage";
    }

    // Работа с юзером ИНОЕ
    @GetMapping("/goToProfilePage")
    public String goToProfilePage(@ModelAttribute("user") User user, Model model) {
        System.out.println("user: " + user.toString());
        model.addAttribute("user", user);
        return "profile-page";
    }

    // Работа с формой с рецептами
    @GetMapping("/showAllRecipes")
    public String showAllRecipes(Model model) {
        Set<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("content", "main-block/recipe-list");
        return "layout/layout";
    }

    @GetMapping("/showCreateForm")
    public String showCreateForm(Model model) {
        // создаем модель, которую будем заполнять в форме, в форме привязываем поля к полям класса
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("content", "main-block/recipe-input-form");
        return "layout/layout";
    }

    @GetMapping("/showUpdateForm")
    public String showUpdateForm(Model model, @RequestParam("recipeId") int id) {
        // выгружаем существующую модель, которую будем изменять в форме
        Recipe existingRecipe = recipeService.getRecipe(id);
        if (existingRecipe != null) {
            model.addAttribute("recipe", existingRecipe);
            model.addAttribute("content", "main-block/recipe-input-form");
        } else {
            return "redirect:/goToStubPage";
        }
        return "layout/layout";
    }

    @PostMapping("/createOrUpdateRecipe")
    public String createOrUpdateRecipe(@ModelAttribute("recipe") Recipe recipe, @SessionAttribute("user") User user) {
        // берем сохраненную модель, отправляем в сервис->бд
        recipeService.createOrUpdateRecipe(recipe, user);
        return "redirect:/showAllRecipes";
    }

    @GetMapping("/deleteRecipe")
    public String deleteRecipe(@RequestParam("recipeId") int id) {
        // в input type="hidden" формы с кнопкой "удалить" привязывали-передавали атрибут айдишника рецепта
        recipeService.deleteRecipe(id);
        return "redirect:/showAllRecipes";
    }

    // пока не реализовано
    @GetMapping("/goToGetMenuPage")
    public String goToGetMenuPage() {
        return "redirect:/goToStubPage";
    }
}