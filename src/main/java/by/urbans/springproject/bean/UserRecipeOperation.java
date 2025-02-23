package by.urbans.springproject.bean;

import by.urbans.springproject.enums.RecipeOperation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        try {
            return "id операции: " + id + "<br>" +
                   "id рецепта: " + (recipe != null ? recipe.getId() : "нет данных") + "<br>" +
                   "наименование рецепта: " + (recipe != null ? recipe.getName() : "нет данных")  + "<br>" +
                   "действие: " + operation  + "<br>" +
                   "время: " + operationTimestamp;
        } catch (Exception e) {
            return "Ошибка в toString(): " + e.getMessage();
        }
    }
}
