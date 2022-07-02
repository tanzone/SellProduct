package it.unipr.warehouse;

import it.unipr.filemanager.FileManager;

import java.util.List;

import static it.unipr.utilities.Constants.ORDERS_SEL;

/**
 * The {@code Order} class, extension of the {@code Product} class, defines an order object:<br>
 * <p>
 * An instance of {@code Object} contains all the data that identifies an order itself,
 * you can set or get any of its attributes and also print it in its own format
 *
 * @author Tanzi Manuel, 307720
 * @author Mamone Maximiliano, 308214
 * @version 1.0
 * @see Product
 * @since 1.0
 */
public class Order extends Product {

    private String orderId;
    private String userId;
    private int quantity;

    /**
     * Class default constructor
     *
     * @since 1.0
     */
    public Order() {
        super();
        this.userId = "";
        this.quantity = 0;
    }

    /**
     * Class constructor with parameters
     *
     * @param id       parameter that contains the identifier of the order itself
     * @param name     parameter that contains the name of the product bought in the order
     * @param brand    parameter that contains the brand of the product bought in the order
     * @param price    parameter that contains the price of the product bought in the order
     * @param userId   parameter that contains the id of the user who made the order
     * @param quantity parameter that contains the quantity ordered by the user
     * @since 1.0
     */
    public Order(String id, String name, String brand, float price, String userId, int quantity) {
        super(id, name, brand, price);
        this.orderId = this.generateOrderId();
        this.userId = userId;
        this.quantity = quantity;
    }

    /**
     * Class constructor with {@code Product} instance as parameter
     *
     * @param p        parameter that contains an instance of {@code Product} used to fill all the common attributes between the two classes
     * @param userId   parameter that contains the id of the user who made the order
     * @param quantity parameter that contains the quantity ordered by the user
     */
    public Order(Product p, String userId, int quantity) {
        super(p);
        this.orderId = this.generateOrderId();
        this.userId = userId;
        this.quantity = quantity;
    }

    /**
     * Getter of the {@code orderId} attribute
     *
     * @return {@code String} - the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Setter of the {@code orderId} attribute
     *
     * @param orderId {@code String} New order id to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Getter of the {@code userId} attribute
     *
     * @return {@code String} - the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter of the {@code userId} attribute
     *
     * @param userId New {@code userId} to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Getter of the {@code quantity} attribute
     *
     * @return {@code int} - the quantity ordered
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter of the {@code quantity} attribute
     *
     * @param quantity New {@code quantity} to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //Method that generates the order id based upon the ids that already exist in the list of orders
    private String generateOrderId() {
        List<Order> orders = FileManager.readData(ORDERS_SEL, Order.class);
        if (orders.isEmpty()) return "0";
        return String.valueOf(Integer.parseInt(orders.get(orders.size() - 1).getOrderId()) + 1);
    }

    /**
     * Method that returns the total cost for that order
     *
     * @return {@code float} - the total cost of the order based upon id and quantity
     * @since 1.0
     */
    public float totalPrice() {
        return this.getPrice() * this.quantity;
    }

    /**
     * Method that creates a formatted {@code String} ready to be printed of the {@code Order} object
     *
     * @return {@code String} - the string ready to be printed
     */
    @Override
    public String toString() {
        return "\nOrder ID: " + this.orderId + "\nUserID: " + this.userId + super.toString() + "\nQuantity: " + this.quantity;
    }
}
