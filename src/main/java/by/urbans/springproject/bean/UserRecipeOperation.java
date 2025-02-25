package by.urbans.springproject.bean;

import by.urbans.springproject.enums.RecipeOperation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "user_recipe_operation")
public class UserRecipeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Поле \"автор\" не заполнено")
    private User recipeAuthor;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    @NotNull(message = "Поле \"рецепт\" не заполнено")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "completed_operation", nullable = false)
    @NotNull(message = "Поле \"операция\" не заполнено")
    private RecipeOperation operation;

    @Column(name = "operation_timestamp", nullable = false)
    @NotNull(message = "Поле \"время операции\" не заполнено")
    private LocalDateTime operationTimestamp;

    public UserRecipeOperation() {
    }

    public UserRecipeOperation(User recipeAuthor, Recipe recipe, RecipeOperation operation, LocalDateTime operationTimestamp) {
        this.recipeAuthor = recipeAuthor;
        this.recipe = recipe;
        this.operation = operation;
        this.operationTimestamp = operationTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Поле \"автор\" не заполнено") User getRecipeAuthor() {
        return recipeAuthor;
    }

    public void setRecipeAuthor(@NotNull(message = "Поле \"автор\" не заполнено") User recipeAuthor) {
        this.recipeAuthor = recipeAuthor;
    }

    public @NotNull(message = "Поле \"рецепт\" не заполнено") Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(@NotNull(message = "Поле \"рецепт\" не заполнено") Recipe recipe) {
        this.recipe = recipe;
    }

    public @NotNull(message = "Поле \"операция\" не заполнено") RecipeOperation getOperation() {
        return operation;
    }

    public void setOperation(@NotNull(message = "Поле \"операция\" не заполнено") RecipeOperation operation) {
        this.operation = operation;
    }

    public @NotNull(message = "Поле \"время операции\" не заполнено") LocalDateTime getOperationTimestamp() {
        return operationTimestamp;
    }

    public void setOperationTimestamp(@NotNull(message = "Поле \"время операции\" не заполнено") LocalDateTime operationTimestamp) {
        this.operationTimestamp = operationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecipeOperation that = (UserRecipeOperation) o;
        return id == that.id && Objects.equals(recipeAuthor, that.recipeAuthor) && Objects.equals(recipe, that.recipe) && operation == that.operation && Objects.equals(operationTimestamp, that.operationTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeAuthor, recipe, operation, operationTimestamp);
    }

    @Override
    public String toString() {
            return "id операции: " + id + "<br>" +
                   "id рецепта: " + recipe.getId() + "<br>" +
                   "наименование рецепта: " + recipe.getName() + "<br>" +
                   "действие: " + operation  + "<br>" +
                   "время: " + operationTimestamp;
    }
}
