/**
 * Represents an item in the inventory.
 *
 * <p>The {@code Item} class provides a structure for managing items with attributes
 * such as a name, an optional image, and a quantity. It includes functionality for
 * comparing items, updating quantities, and determining if an item is out of stock.</p>
 *
 * @author Bashar Hamo
 */
package org.example;

import javafx.scene.image.ImageView;

import java.util.Objects;

public class Item {

    private String name;  // The name of the item
    private ImageView image;  // The image representing the item (optional)
    private int quantity;  // The quantity of the item in stock

    /**
     * Constructs an {@code Item} object with the specified name and quantity.
     *
     * @param name     the name of the item
     * @param quantity the quantity of the item
     */
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Retrieves the quantity of the item.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Retrieves the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the image for the item.
     *
     * @param image an {@code ImageView} object representing the item's image
     */
    public void setImage(ImageView image) {
        this.image = image;
    }

    /**
     * Sets the quantity of the item.
     *
     * @param quantity the new quantity of the item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Decreases the quantity of the item by the specified amount.
     *
     * @param amount the amount to decrease the quantity by
     */
    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }

    /**
     * Checks if the item is out of stock (quantity less than or equal to zero).
     *
     * @return {@code true} if the item is out of stock, {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.quantity <= 0;
    }

    /**
     * Compares this item to another object for equality based on the name.
     *
     * @param o the object to compare to
     * @return {@code true} if the objects are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    /**
     * Generates a hash code for this item based on the name field.
     *
     * @return a hash code for the item
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Returns a string representation of the item.
     *
     * <p>The string includes the name of the item.</p>
     *
     * @return a string representation of the {@code Item} object
     */
    @Override
    public String toString() {
        return "Item{name='" + name + "'}";
    }
}
