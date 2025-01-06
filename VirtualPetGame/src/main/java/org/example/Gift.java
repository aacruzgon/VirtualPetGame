/**
 * Represents a gift item, which is a type of {@code Item}.
 *
 * <p>The {@code Gift} class includes attributes specific to gift items, such as a value
 * that indicates the worth of the gift. The category of this item is always "Gift".</p>
 *
 * @author Bashar Hamo
 */
package org.example;

public class Gift extends Item {

    private final String category = "Gift"; // Fixed category for Gift items
    private int value;

    /**
     * Constructs a {@code Gift} object with the specified name, value, and quantity.
     *
     * @param name     the name of the gift item
     * @param value    the value of the gift item
     * @param quantity the quantity of the gift item
     */
    public Gift(String name, int value, int quantity) {
        super(name, quantity); // Pass the name and quantity to the parent class
        this.value = value;
    }

    /**
     * Returns the category of the item, which is always "Gift".
     *
     * @return the string "Gift"
     */
    public String getCategory() {
        return category; // Always returns "Gift"
    }

    /**
     * Returns the value of the gift item.
     *
     * @return the value of the gift item
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of the gift item.
     *
     * <p>The string includes the name, value, and quantity of the gift item.</p>
     *
     * @return a string representation of the {@code Gift} object
     */
    @Override
    public String toString() {
        return "Gift{name='" + getName() + "', value=" + value + ", quantity=" + getQuantity() + "}";
    }
}

