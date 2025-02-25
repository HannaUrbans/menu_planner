package by.urbans.springproject.bean;

import by.urbans.springproject.enums.ProductAllergen;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

// возможно, будет переделан со спринг security?
@Entity
@Table(name = "client_details")
public class Client extends User {

    @ElementCollection(targetClass = ProductAllergen.class)
    @CollectionTable(name = "product_allergens", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "allergen")
    private List<ProductAllergen> productAllergenList;

    @NotEmpty
    @Min(value = 1000, message = "ВОЗ советует употреблять не менее 1000 калорий в день")
    @Column(name = "daily_target_calories")
    private int dailyTargetCalories;

    public Client() {
    }

    public Client(List<ProductAllergen> productAllergenList, int dailyTargetCalories) {
        this.productAllergenList = productAllergenList;
        this.dailyTargetCalories = dailyTargetCalories;
    }

    public List<ProductAllergen> getProductAllergenList() {
        return productAllergenList;
    }

    public void setProductAllergenList(List<ProductAllergen> productAllergenList) {
        this.productAllergenList = productAllergenList;
    }

    public int getDailyTargetCalories() {
        return dailyTargetCalories;
    }

    public void setDailyTargetCalories(int dailyTargetCalories) {
        this.dailyTargetCalories = dailyTargetCalories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return dailyTargetCalories == client.dailyTargetCalories &&
               Objects.equals(productAllergenList, client.productAllergenList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), productAllergenList, dailyTargetCalories);
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
               "productAllergenList=" + productAllergenList +
               ", dailyTargetCalories=" + dailyTargetCalories +
               '}';
    }

}
