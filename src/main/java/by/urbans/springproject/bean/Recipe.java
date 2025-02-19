package by.urbans.springproject.bean;

import by.urbans.springproject.enums.MealCategory;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    //эта аннотация используется, если выбирается только ОДНО значение из енама
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private MealCategory mealCategory;

    @Column(name = "caloric_value")
    private float caloricValue;

    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "description")
    private String description;

   @ManyToMany
   @JoinTable(name="recipe_author",
   // внешний ключ, который указывает на текущую таблицу (первый столбец)
   joinColumns = @JoinColumn(name = "recipe_id"),
   //внешний ключ, который указывает на связанную таблицу (второй столбец)
   inverseJoinColumns =  @JoinColumn (name = "user_id")
   )
    private Set<User> authorSet;

   @ManyToMany
    private Set<UserRecipeOperation> recipeOperations;

    public Recipe() {
    }

//    public Recipe(String name, MealCategory mealCategory, float caloricValue, String ingredients, String description, Set<User> author, Set<UserRecipeOperation> recipeOperations) {
//        this.name = name;
//        this.mealCategory = mealCategory;
//        this.caloricValue = caloricValue;
//        this.ingredients = ingredients;
//        this.description = description;
////        this.author = author;
////        this.recipeOperations = recipeOperations;
//    }

//    public Recipe(int id, String name, MealCategory mealCategory, float caloricValue, String ingredients, String description, Set<User> author, Set<UserRecipeOperation> recipeOperations) {
//        this.id = id;
//        this.name = name;
//        this.mealCategory = mealCategory;
//        this.caloricValue = caloricValue;
//        this.ingredients = ingredients;
//        this.description = description;
////        this.author = author;
////        this.recipeOperations = recipeOperations;
//    }

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
        return id == recipe.id && Float.compare(caloricValue, recipe.caloricValue) == 0 && Objects.equals(name, recipe.name) && mealCategory == recipe.mealCategory && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(description, recipe.description) && Objects.equals(authorSet, recipe.authorSet) && Objects.equals(recipeOperations, recipe.recipeOperations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, mealCategory, caloricValue, ingredients, description, authorSet, recipeOperations);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", mealCategory=" + mealCategory +
               ", caloricValue=" + caloricValue +
               ", ingredients='" + ingredients + '\'' +
               ", description='" + description + '\'' +
               ", authors=" + authorSet +
               ", recipeOperations=" + recipeOperations +
               '}';
    }
}
