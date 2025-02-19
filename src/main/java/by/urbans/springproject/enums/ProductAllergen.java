package by.urbans.springproject.enums;

public enum ProductAllergen {
    MILK("молоко"),
    SOYBEANS("соя"),
    WHEAT("пшеница"),
    PEANUTS("арахис"),
    SHELLFISH("моллюски"),
    EGGS("яйца"),
    NUTS("орехи"),
    FISH("рыба");

    private final String description;

    ProductAllergen(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
