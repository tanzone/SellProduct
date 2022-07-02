package it.unipr.accounts;

import it.unipr.filemanager.FileManager;
import it.unipr.warehouse.Product;
import it.unipr.warehouse.Warehouse;

import java.util.HashMap;
import java.util.List;

import static it.unipr.utilities.Constants.EMPLOYEES_SEL;
import static it.unipr.utilities.Constants.USER_SEL;

/**
 * The {@code Admin} class, as a child class of {@code Employee}, provides all the methods to administrate
 * the entire system in addition to having all the {@code Employee}'s methods
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @see Employee
 * @since 1.0
 */
public class Admin extends Employee {
    /**
     * Class constructor with parameters
     *
     * @param username variable used to define the username
     * @param password variable used to define the password
     * @since 1.0
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Class copy constructor extended from superclass
     *
     * @param o used to create a {@code Admin} object with the parameters of a {@code Person}
     * @see Person
     * @since 1.0
     */
    public Admin(Person o) {
        super(o);
    }

    /**
     * Method to get all the employees contained in the database
     *
     * @return {@code List<Person>} - the list of all employees
     * @see Person
     * @since 1.0
     */
    public List<Person> getEmployees() {
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.remove(0);
        return list;
    }

    /**
     * Method to get all the users contained in the database
     *
     * @return {@code List<Person>} - the list of all users
     * @see Person
     * @since 1.0
     */
    public List<Person> getUsers() {
        return FileManager.readData(USER_SEL, Person.class);
    }


    /**
     * Method that returns the warehouse reading it from the database
     *
     * @return {@code HashMap<Product, Integer>} - the map with {@code Product} and the quantity
     * @see Product
     * @since 1.0
     */
    public HashMap<Product, Integer> getWarehouse() {
        return FileManager.readMap();
    }

    /**
     * Method that checks if an employee with the username passed as parameter exists
     *
     * @param username value used to control on the employee's database
     * @return {@code boolean} - true if exist user with that username, false otherwise
     * @since 1.0
     */
    public boolean checkEmployeeExist(String username) {
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.remove(0);
        for (Person p : list)
            if (p.getUsername().equals(username))
                return true;
        return false;
    }

    /**
     * Method that adds an employee to the database
     *
     * @param p person to add to the database
     * @see Person
     * @since 1.0
     */
    public void addEmployee(Person p) {
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.add(p);
        FileManager.writeData(list, EMPLOYEES_SEL);
    }

    /**
     * Method that removes an employee from the database
     *
     * @param username value used as a reference to remove the employee from the database
     * @since 1.0
     */
    public void removeEmployee(String username) {
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.removeIf(p -> p.getUsername().equals(username));
        FileManager.writeData(list, EMPLOYEES_SEL);
    }

    /**
     * Method that checks if a user with the username passed as parameter exists
     *
     * @param username value used to control on the user's database
     * @return {@code boolean} - true if the user with that username exists, false otherwise
     * @since 1.0
     */
    public boolean checkUserExist(String username) {
        List<Person> list = FileManager.readData(USER_SEL, Person.class);
        for (Person p : list)
            if (p.getUsername().equals(username))
                return true;
        return false;
    }

    /**
     * Method that adds a user to the database
     *
     * @param p person to add to the database
     * @see Person
     * @since 1.0
     */
    public void addUser(Person p) {
        List<Person> list = FileManager.readData(USER_SEL, Person.class);
        list.add(p);
        FileManager.writeData(list, USER_SEL);
    }

    /**
     * Method that removes an employee from the database
     *
     * @param username value used as a reference to remove the user from the database
     * @since 1.0
     */
    public void removeUser(String username) {
        List<Person> list = FileManager.readData(USER_SEL, Person.class);
        list.removeIf(p -> p.getUsername().equals(username));
        FileManager.writeData(list, USER_SEL);
    }

    /**
     * Method that checks if a user with the id passed as parameter exists
     *
     * @param id        value used to control on the product's database
     * @param warehouse is the place where the products are stocked
     * @return {@code boolean} - true if the product with that id exists, false otherwise
     * @since 2.0
     */
    public boolean checkProductExist(String id, Warehouse warehouse) {
        for (Product key : warehouse.getStorage().keySet())
            if (key.getId().equals(id))
                return true;
        return false;
    }

    /**
     * Method that add a product to the database with the specified quantity
     *
     * @param p         product to add to the database
     * @param quantity  number of product to add
     * @param warehouse is the place where the products are stocked
     * @see Product
     * @since 2.0
     */
    public void addProduct(Product p, int quantity, Warehouse warehouse) {
        warehouse.getStorage().put(p, quantity);
        FileManager.writeMap(warehouse.getStorage());
    }

    /**
     * Method that removes the product with the specified id from the database
     *
     * @param id        value used as a reference to remove the product from the database
     * @param warehouse is the place where the products are stocked
     * @since 2.0
     */
    public void removeProduct(String id, Warehouse warehouse) {
        warehouse.getStorage().entrySet().removeIf(p -> p.getKey().getId().equals(id));
        FileManager.writeMap(warehouse.getStorage());
    }
}
