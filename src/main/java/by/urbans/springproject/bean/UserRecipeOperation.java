package by.urbans.springproject.bean;

import by.urbans.springproject.enums.RecipeOperation;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_recipe_operation")
public class UserRecipeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name= "user_id")
    private User recipeAuthor;

    @ManyToOne
    @JoinColumn (name = "recipe_id")
    private Recipe recipe;

    @Enumerated(EnumType.STRING)
    @Column(name = "completed_operation")
    private RecipeOperation operation;

    @Column(name = "operation_timestamp")
    private LocalDateTime operationTimestamp;

    public UserRecipeOperation() {
    }


}
