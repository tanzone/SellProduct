package it.unipr.utilities;

import it.unipr.warehouse.Order;
import it.unipr.warehouse.Product;

/**
 * This interface defines abstract methods that are defined in different classes,
 * providing different functionalities to different parts of the application
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public interface MyListener {
    /**
     * This abstract method when implemented intercepts the click action for the visualization of a single products
     *
     * @param product the product to visualize
     * @param quantity the quantity of the product to visualize
     */
    void clickShowProduct(Product product, int quantity);

    /**
     * This abstract method when implemented intercepts the click action for the deletion of a user account
     *
     * @param username the username that belongs to the user account to delete
     */
    void clickDeleteUser(String username);

    /**
     * This abstract method when implemented intercepts the click action for the deletion of an employee account
     *
     * @param username the username that belongs to the employee account to delete
     */
    void clickDeleteEmployee(String username);

    /**
     * This abstract method when implemented intercepts the click action for the deletion of a product
     *
     * @param id the id that belongs to the product to delete
     */
    void clickDeleteProduct(String id);

    /**
     * This abstract method when implemented intercepts the click action for the deletion of an order
     *
     * @param o the instance of {@code Order} containing the order to delete
     */
    void clickDeleteOrder(Order o);
}
