package by.urbans.springproject.bean;

import java.util.Objects;
import java.util.Set;

// пока не трогала
public class Menu {
    private int id;
    private int dayNumber;
    private Set<Recipe> breakfast;
    private Set<Recipe> dinner;
    private Set<Recipe> supper;

    public Menu(Set<Recipe> breakfast, Set<Recipe> dinner, Set<Recipe> supper) {
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.supper = supper;
    }

    public Menu(int dayNumber, Set<Recipe> breakfast, Set<Recipe> dinner, Set<Recipe> supper) {
        this.dayNumber = dayNumber;
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.supper = supper;
    }

    public Menu(int id, int dayNumber, Set<Recipe> breakfast, Set<Recipe> dinner, Set<Recipe> supper) {
        this.id = id;
        this.dayNumber = dayNumber;
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.supper = supper;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Set<Recipe> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Set<Recipe> breakfast) {
        this.breakfast = breakfast;
    }

    public Set<Recipe> getDinner() {
        return dinner;
    }

    public void setDinner(Set<Recipe> dinner) {
        this.dinner = dinner;
    }

    public Set<Recipe> getSupper() {
        return supper;
    }

    public void setSupper(Set<Recipe> supper) {
        this.supper = supper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id && dayNumber == menu.dayNumber && Objects.equals(breakfast, menu.breakfast) && Objects.equals(dinner, menu.dinner) && Objects.equals(supper, menu.supper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dayNumber, breakfast, dinner, supper);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "id=" + id +
               ", breakfast=" + breakfast +
               ", dinner=" + dinner +
               ", supper=" + supper +
               '}';
    }
}
