package by.urbans.springproject.enums;

public enum MealCategory {
    BREAKFAST("завтрак"), DINNER("обед"), SUPPER("ужин");

    private final String categoryName;

    MealCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
