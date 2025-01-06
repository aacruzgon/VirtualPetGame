/**
 * Represents a food item, which is a type of {@code Item}.
 *
 * <p>The Food class includes attributes specific to food items, such as the
 * fullness value that indicates how much the food satisfies hunger. The category
 * of this item is always "Food".</p>
 *
 * @author Bashar Hamo
 */
package org.example;

public class Food extends Item {

    private final String category = "Food"; // Fixed category for Food items
    private int fullness;

    /**
     * Constructs a {@code Food} object with the specified name, fullness value, and quantity.
     *
     * @param name     the name of the food item
     * @param fullness the fullness value provided by the food item
     * @param quantity the quantity of the food item
     */
    public Food(String name, int fullness, int quantity) {
        super(name, quantity); // Pass the name and quantity to the parent class
        this.fullness = fullness;
    }

    /**
     * Returns the category of the item, which is always "Food".
     *
     * @return the string "Food"
     */
    public String getCategory() {
        return category; // Always returns "Food"
    }

    /**
     * Returns the fullness value of the food item.
     *
     * @return the fullness value
     */
    public int getFullness() {
        return fullness;
    }

    /**
     * Returns a string representation of the food item.
     *
     * <p>The string includes the name, fullness value, and quantity of the food item.</p>
     *
     * @return a string representation of the {@code Food} object
     */
    @Override
    public String toString() {
        return "Food{name='" + getName() + "', fullness=" + fullness + ", quantity=" + getQuantity() + "}";
    }
}
