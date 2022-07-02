package it.unipr.accounts;

import it.unipr.filemanager.FileManager;
import it.unipr.warehouse.Order;

import java.util.ArrayList;
import java.util.List;

import static it.unipr.utilities.Constants.ORDERS_SEL;

/**
 * The {@code User} class, as a child class of {@code Person}, provides all the necessary methods and attributes for all the user tasks
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see Person
 * @since 1.0
 */
public class User extends Person {
    private final List<Order> boughtOrder;

    /**
     * Class constructor with parameters
     *
     * @param username variable used to define the username
     * @param password variable used to define the password
     * @since 1.0
     */
    public User(String username, String password) {
        super(username, password);
        this.boughtOrder = new ArrayList<>();
    }

    /**
     * Class copy constructor extended from superclass
     *
     * @param o used to create a {@code User} object with the parameters of a {@code Person}
     * @see Person
     * @since 1.0
     */
    public User(Person o) {
        super(o);
        this.boughtOrder = new ArrayList<>();
    }

    /**
     * Getter of the bought orders attribute
     *
     * @return {@code List<Order>} - list of bought orders of the current session
     */
    public List<Order> getBoughtOrder() {
        return boughtOrder;
    }

    /**
     * Method that reads the orders from the database and saves only those belonging to this User
     *
     * @return {@code List<Order>} the list of present orders made by this User
     * @since 1.0
     */
    public List<Order> getAllOrders() {
        List<Order> allOrders = FileManager.readData(ORDERS_SEL, Order.class);
        allOrders.removeIf(o -> !o.getUserId().equals(this.getUsername()));
        return allOrders;
    }

    /**
     * Method that creates a customized {@code String} with a welcome message
     *
     * @return {@code String} - welcome User to print
     * @since 1.0
     */
    public String welcome() {
        return "\n\nWelcome user - " + this.getUsername() + "\n";
    }


    /**
     * Method that adds the order to the database and to the session's order list
     *
     * @param p the order to add to the database
     */
    public void addBoughtOrder(Order p) {
        this.boughtOrder.add(p);
        List<Order> allOrders = FileManager.readData(ORDERS_SEL, Order.class);
        allOrders.add(p);
        FileManager.writeData(allOrders, ORDERS_SEL);
    }

    /**
     * Override method used to print object attributes
     *
     * @return {@code String} - a string with a custom format containing the bought order
     * @since 1.0
     */
    @Override
    public String toString() {
        return "User {" + "boughtOrder : " + boughtOrder + '}';
    }
}
