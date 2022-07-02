package it.unipr.accounts;

import it.unipr.filemanager.FileManager;
import it.unipr.warehouse.Order;
import it.unipr.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

import static it.unipr.utilities.Constants.*;

/**
 * The {@code Employee}, as a child class of {@code Person}, provides all the necessary methods to complete the tasks
 * of an employee.
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @see Person
 * @since 1.0
 */
public class Employee extends Person {
    /**
     * Class constructor with parameters
     *
     * @param username variable used to define the username
     * @param password variable used to define the password
     * @since 1.0
     */
    public Employee(String username, String password) {
        super(username, password);
    }

    /**
     * Class copy constructor extended from superclass
     *
     * @param o used to create a {@code Employee} object with the parameters of a {@code Person}
     * @see Person
     * @since 1.0
     */
    public Employee(Person o) {
        super(o);
    }

    /**
     * Method that returns a {@code String} with a welcoming message for the employee
     *
     * @return {@code String} - welcome Employee to print
     * @since 1.0
     */
    public String welcome() {
        return "\n\nWelcome Employer - " + this.getUsername() + "\n";
    }


    /**
     * Method that "ships" an order according to some basic parameters: product id, user id, order id or all orders regardless,
     * bound however by the availability of the warehouse
     *
     * @param id        used to identify special field
     * @param method    describe the method used to ship the order "ALL", "ORDERS", "USERS", "PRODUCTS"
     * @param warehouse warehouse from where control the order and check availability
     * @since 1.0
     */
    public void shipOrder(String id, String method, Warehouse warehouse) {
        List<Order> orders = FileManager.readData(ORDERS_SEL, Order.class);

        for (Order o : orders) {
            if (method.equals(ORDERS) && o.getOrderId().equals(id))
                this.setParameters(o, warehouse);
            else if (method.equals(PRODUCTS) && o.getId().equals(id))
                this.setParameters(o, warehouse);
            else if (method.equals(USERS) && o.getUserId().equals(id))
                this.setParameters(o, warehouse);
            else if (method.equals(ALL))
                this.setParameters(o, warehouse);

        }
        orders.removeIf(o -> o.getQuantity() == 0);
        this.updateDatabases(orders, warehouse, true);
    }

    //Used to set quantity on the warehouse and decrease the quantity of specific Order passed like parameters
    private void setParameters(Order o, Warehouse warehouse) {
        if (o.getQuantity() <= warehouse.getStorage().get(warehouse.getProductById(o.getId()))) {
            warehouse.getStorage().put(warehouse.getProductById(o.getId()), warehouse.getStorage().get(warehouse.getProductById(o.getId())) - o.getQuantity());
            o.setQuantity(0);
        } else if (o.getQuantity() > warehouse.getStorage().get(warehouse.getProductById(o.getId()))) {
            o.setQuantity(o.getQuantity() - warehouse.getStorage().get(warehouse.getProductById(o.getId())));
            warehouse.getStorage().put(warehouse.getProductById(o.getId()), 0);
        }
    }

    /**
     * Method that replenishes the warehouse of some or all products
     *
     * @param id        identify the product to manage
     * @param quantity  the number do you want
     * @param method    "ALL" replenish the storage of all products with quantity 0, "PRODUCT" replenish just the product identified
     * @param warehouse warehouse from where upgrade the storage
     * @since 2.0
     */
    public void replenish(String id, int quantity, String method, Warehouse warehouse) {
        warehouse.setStorage(FileManager.readMap());

        for (var entry : warehouse.getStorage().entrySet()) {
            if (method.equals(ALL))
                warehouse.getStorage().put(entry.getKey(), entry.getValue() + quantity);
            else if (method.equals(OUT_OF_STOCKS) && entry.getValue() == 0)
                warehouse.getStorage().put(entry.getKey(), entry.getValue() + quantity);
            else if (method.equals(PRODUCTS) && entry.getKey().getId().equals(id)) //&& entry.getValue() == 0)
                warehouse.getStorage().put(entry.getKey(), entry.getValue() + quantity);
        }
        this.updateDatabases(new ArrayList<>(), warehouse, false);
    }

    //Update the databases
    private void updateDatabases(List<Order> orders, Warehouse warehouse, boolean sendOrders) {
        if (sendOrders) FileManager.writeData(orders, ORDERS_SEL);

        FileManager.writeMap(warehouse.getStorage());
    }
}
