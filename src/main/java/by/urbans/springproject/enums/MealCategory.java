package by.urbans.springproject.enums;

public enum MealCategory {
    BREAKFAST("Завтрак"), LUNCH("Обед"), DINNER("Ужин");

    private final String categoryName;

    MealCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
