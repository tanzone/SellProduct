package it.unipr.warehouse;

import it.unipr.filemanager.FileManager;

import java.util.*;
import java.util.stream.Collectors;

import static it.unipr.utilities.Constants.*;

/**
 * The {@code Warehouse} class defines a warehouse object<br>
 * <p>
 * An instance of {@code Warhouse} is capable of managing lists of {@code Order} objects and maps of {@code Product} objects,
 * it can filter, check availability and add orders.
 *
 * @author Tanzi Manuel, 307720
 * @author Mamone Maximiliano, 308214
 * @version 2.0
 * @see Product
 * @see Order
 * @since 1.0
 */
public class Warehouse {
    private final HashMap<String, String> map;
    private HashMap<Product, Integer> storage;

    /**
     * Class default constructor
     *
     * @since 1.0
     */
    public Warehouse() {
        this.storage = new HashMap<>();
        this.map = new HashMap<>();
        for (String s : FILTERMAP)
            this.map.put(s, "");
    }

    /**
     * Class constructor with parameters
     *
     * @param storage Parameter used to set the product map attribute
     * @since 1.0
     */
    public Warehouse(HashMap<Product, Integer> storage) {
        this.storage = storage;
        this.map = new HashMap<>();
        for (String s : FILTERMAP)
            this.map.put(s, "");
    }

    /**
     * Getter of the storage attribute
     *
     * @return {@code HashMap<Product, Integer>} - The storage of the warehouse
     * @since 1.0
     */
    public HashMap<Product, Integer> getStorage() {
        return storage;
    }

    /**
     * Setter of the storage attribute
     *
     * @param storage New storage to set
     * @since 1.0
     */
    public void setStorage(HashMap<Product, Integer> storage) {
        this.storage = storage;
    }

    /**
     * Method used to add an entry to the filter map, {@code s1,s2} are respectively the key and the value to set
     * <p>
     * In this particular class the method sets a particular filter if the user chooses to
     *
     * @param s1 {@code String} that contains the filter to add
     * @param s2 {@code String} that contains the value to use as a filter
     * @since 1.0
     */
    public void addToMap(String s1, String s2) {
        this.map.put(s1, s2);
    }

    /**
     * Method that empties the filtering map and readies it for the next use
     *
     * @since 1.0
     */
    public void resetFilter() {
        for (String s : FILTERMAP)
            this.map.put(s, "");
    }

    /**
     * Method that applies the filtering (thorough the filtering map) to the {@code storage} attribute, returning a map of products with the desired characteristics
     *
     * @return {@code HashMap<Product, Integer>} - New map containing the filtered products
     * @since 1.0
     */
    public HashMap<Product, Integer> filterProduct() {
        Map<Product, Integer> filteredMap = this.storage;

        if (!this.map.get(FILTERMAP[0]).equals(""))
            filteredMap = filteredMap.entrySet().stream().filter(map -> map.getKey().getId().equals(this.map.get(FILTERMAP[0])))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!this.map.get(FILTERMAP[1]).equals(""))
            filteredMap = filteredMap.entrySet().stream().filter(p -> p.getKey().getName().toLowerCase().contains(this.map.get(FILTERMAP[1])))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!this.map.get(FILTERMAP[2]).equals(""))
            filteredMap = filteredMap.entrySet().stream().filter(p -> p.getKey().getBrand().toLowerCase().contains(this.map.get(FILTERMAP[2])))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!this.map.get(FILTERMAP[3]).equals(""))
            filteredMap = filteredMap.entrySet().stream().filter(p -> p.getKey().getPrice() >= Float.parseFloat(this.map.get(FILTERMAP[3])))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (!this.map.get(FILTERMAP[4]).equals(""))
            filteredMap = filteredMap.entrySet().stream().filter(p -> p.getKey().getPrice() <= Float.parseFloat(this.map.get(FILTERMAP[4])))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return (HashMap<Product, Integer>) filteredMap;
    }

    /**
     * Method that returns an ordered map of products based on the parameter {@code boolean desc}
     *
     * @param desc    parameter that identifies if the map has to be ordered in descending or ascending order - {@code true} descending {@code false} ascending
     * @param sortMap paramater that must contain the map to order
     * @return {@code HashMap<Product, Integer>} - sorted map
     * @since 2.0
     */
    public HashMap<Product, Integer> orderMap(boolean desc, Map<Product, Integer> sortMap) {
        if (!desc)
            return sortMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        else
            return sortMap.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Method that returns from the storage a product with an equal id to the one passed as parameter
     *
     * @param id parameter that identifies the product to look for
     * @return {@code Product} - if it finds a correspondence it returns the found product otherwise it returns a new empty product
     * @since 1.0
     */
    public Product getProductById(String id) {
        for (Map.Entry<Product, Integer> entry : this.storage.entrySet())
            if (entry.getKey().getId().equals(id))
                return entry.getKey();
        return new Product();
    }

    /**
     * Method that checks if a product is present in a certain quantity in the storage
     *
     * @param id       parameter that identifies the product to look for
     * @param quantity parameter that identifies the quantity needed
     * @return {@code boolean} - true if the product exists and there is enough of it, false otherwise
     * @since 1.0
     */
    public boolean checkProduct(String id, int quantity) {
        if (this.storage.containsKey(this.getProductById(id)))
            return quantity <= this.storage.get(this.getProductById(id));
        return false;
    }

    /**
     * Method that checks if a product exists
     *
     * @param id parameter that identifies the product to look for
     * @return {@code boolean} - true if the product is a key of the {@code storage} map, false otherwise
     * @since 1.0
     */
    public boolean checkProductExist(String id) {
        return this.storage.containsKey(this.getProductById(id));
    }

    /**
     * Method that receives id and quantity as parameters and returns the total cost for that order
     *
     * @param id       parameter that identifies the product to look for
     * @param quantity parameter that identifies the quantity ordered
     * @return {@code float} - the total cost of the order based upon id and quantity parameters
     * @since 1.0
     */
    public float totalPriceOrder(String id, int quantity) {
        return this.getProductById(id).getPrice() * quantity;
    }

    /**
     * Method that uses product id, quantity, and user id to create a new {@code Order}
     *
     * @param id       parameter that identifies the product
     * @param quantity parameter that identifies how much of the product has to be ordered
     * @param userId   parameter that identifies the user placing the order
     * @return {@code Order} - new instance of Order
     * @since 1.0
     */
    public Order buyProduct(String id, int quantity, String userId) {
        Product bought = this.getProductById(id);

        this.storage.put(bought, this.storage.get(bought) - quantity);

        return new Order(bought, userId, quantity);
    }

    /**
     * Method that can check if an order exists in three ways, through user id, product id or order id
     *
     * @param id     parameter that identifies the order or orders we are looking for
     * @param method parameter that identifies the type of id we are looking for (product, order, user)
     * @return {@code boolean} true if there is at least one correspondence, false otherwise
     * @since 1.0
     */
    public boolean checkAvailability(String id, String method) {
        List<Order> orders = FileManager.readData(ORDERS_SEL, Order.class);
        for (Order o : orders) {
            if (method.equals(ORDERS) && o.getOrderId().equals(id) && this.checkProductAvailability(o.getId(), false))
                return true;
            if (method.equals(USERS) && o.getUserId().equals(id))
                return true;
            if (method.equals(PRODUCTS) && o.getId().equals(id))
                return true;
        }
        return false;
    }

    /**
     * Method that checks if a product exists in the storage and if its quantity is more than 0 or equal to 0
     *
     * @param id    parameter that identifies the product to look for
     * @param empty parameter {@code boolean} that modifies the return of the function, if true the function will check for an out-of-stock product (quantity zero), more than 0 otherwise
     * @return {@code boolean} true if the product exist and is more than zero (with {@code empty} false) or equal to zero (with {@code empty} true), false otherwise
     * @since 1.0
     */
    public boolean checkProductAvailability(String id, boolean empty) {
        if (!empty)
            return (this.checkProductExist(id) && this.storage.get(this.getProductById(id)) > 0);
        else
            return (this.checkProductExist(id) && this.storage.get(this.getProductById(id)) == 0);
    }


    /**
     * Method that return just the items out of stocks, with quantity equal to zero
     *
     * @return {@code HashMap<Product, Integer>} - map of out of stock products
     * @since 2.0
     */
    public HashMap<Product, Integer> getOutOfStocks() {
        HashMap<Product, Integer> outOfStocks = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : this.storage.entrySet())
            if (entry.getValue() == 0)
                outOfStocks.put(entry.getKey(), 0);
        return outOfStocks;
    }

    /**
     * Method that prints the storage map in three possible ways based upon the {@code type} parameter
     *
     * @param type parameter that identifies if all the products need to be printed or just the available or the out-of-stock ones
     * @since 1.0
     */
    public void printStorage(String type) {
        this.storage.forEach((k, v) ->
        {
            if (type.equals("ALL"))
                System.out.println(k.toString() + "\nQuantity: " + v + "\n");
            if (type.equals("AVAILABLE") && v > 0)
                System.out.println(k.toString() + "\nQuantity: " + v + "\n");
            if (type.equals("EMPTY") && v == 0)
                System.out.println(k.toString() + "\nQuantity: " + v + "\n");
        });
    }

    /**
     * Method that prints the map passed as parameter
     *
     * @param storage parameter that contains the map to print
     * @since 1.0
     */
    public void printStorage(HashMap<Product, Integer> storage) {
        storage.forEach((k, v) -> System.out.println(k.toString() + "\nQuantity: " + v + "\n"));
    }

    /**
     * Method that prints any type of list received as parameter
     *
     * @param list parameter containing the list to print
     * @since 1.0
     */
    public void printList(List<?> list) {
        list.forEach(System.out::println);
    }

}
