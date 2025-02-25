package by.urbans.springproject.bean;

import by.urbans.springproject.enums.MealCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Поле \"название\" не заполнено")
    private String name;

    //эта аннотация используется, если выбирается только ОДНО значение из енама
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    @NotNull(message = "Поле \"категория\" не заполнено")
    private MealCategory mealCategory;

    @Column(name = "caloric_value", nullable = false)
    @NotNull(message = "Поле \"калорийность\" не заполнено")
    private float caloricValue;

    /*впоследствии переделать в List<String>*/
    @Column(name = "ingredients", nullable = false)
    @NotEmpty(message = "Поле \"ингредиенты\" не заполнено")
    private String ingredients;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "Поле \"описание\" не заполнено")
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "recipe_author",
            // внешний ключ, который указывает на текущую таблицу (первый столбец)
            joinColumns = @JoinColumn(name = "recipe_id"),
            //внешний ключ, который указывает на связанную таблицу (второй столбец)
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> authorSet;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<UserRecipeOperation> recipeOperations;

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MealCategory getMealCategory() {
        return mealCategory;
    }

    public void setMealCategory(MealCategory mealCategory) {
        this.mealCategory = mealCategory;
    }

    public float getCaloricValue() {
        return caloricValue;
    }

    public void setCaloricValue(float caloricValue) {
        this.caloricValue = caloricValue;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Set<User> getAuthorSet() {
        return authorSet;
    }

    public void setAuthorSet(Set<User> authorSet) {
        this.authorSet = authorSet;
    }

    public Set<UserRecipeOperation> getRecipeOperations() {
        return recipeOperations;
    }

    public void setRecipeOperations(Set<UserRecipeOperation> recipeOperations) {
        this.recipeOperations = recipeOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Float.compare(caloricValue, recipe.caloricValue) == 0 && Objects.equals(name, recipe.name) && mealCategory == recipe.mealCategory && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(description, recipe.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mealCategory, caloricValue, ingredients, description);
    }

    @Override
    public String toString() {
        return "id рецепта: " + id + "<br>" +
               "наименование: " + name + "<br>" +
               "категория: " + mealCategory + "<br>" +
               "калорийность: " + caloricValue + "<br>" +
               "ингредиенты: " + ingredients + "<br>" +
               "описание: " + description;
    }
}
