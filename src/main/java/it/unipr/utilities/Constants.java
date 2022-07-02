package it.unipr.utilities;

import it.unipr.filemanager.FileManager;

/**
 * The {@code Constants} class defines the utilities needed for the proper funcitoning of the software
 *
 * @author Tanzi Manuel, 307720
 * @author Mamone Maximiliano, 308214
 * @version 1.0
 * @since 1.0
 */
public final class Constants {

    /**
     * selected option to quit back
     */
    public static final int QUIT_BACK = 0;
    /**
     * selected option to user login
     */
    public static final int USER_LOGIN = 1;
    /**
     * selected option to employee login
     */
    public static final int EMPLOYEE_LOGIN = 2;
    /**
     * selected option to user login
     */
    public static final int USER_SIGN = 3;
    /**
     * describe all main keys for build the filter map
     */
    public static final String[] FILTERMAP = {"id", "name", "brand", "priceMin", "priceMax"};
    /**
     * path to resources folder
     */
    public static final String DATAFILE = "./src/main/resources/";
    /**
     * file extension
     */
    public static final String EXTENSION = ".json";
    /**
     * name of the users' accounts file
     */
    public static final String USERS = "Users";
    /**
     * name of the employees' accounts file
     */
    public static final String EMPLOYEES = "Employees";
    /**
     * name of the product's list file
     */
    public static final String PRODUCTS = "Products";
    /**
     * name of the order's list file
     */
    public static final String ORDERS = "Orders";
    /**
     * constant used to indicate that all the things had to be done
     */
    public static final String ALL = "All";
    /**
     * Indicate the out of stocks parameters
     */
    public static final String OUT_OF_STOCKS = "Out of Stocks";
    /**
     * used to ask the {@code FileManager} class the products' file in form of a map
     *
     * @see FileManager
     */
    public static final int PRODUCTS_SEL = 0;
    /**
     * used to ask the {@code FileManager} class the users' file in form of a list
     *
     * @see FileManager
     */
    public static final int USER_SEL = 1;
    /**
     * used to ask the {@code FileManager} class the employees' file in form of a list
     *
     * @see FileManager
     */
    public static final int EMPLOYEES_SEL = 2;
    /**
     * used to ask the {@code FileManager} class the orders' file in form of a list
     *
     * @see FileManager
     */
    public static final int ORDERS_SEL = 3;


    /**
     * used to fill the {@code ComboBox} in the login scene
     *
     * @see it.unipr.controllers.login.LoginController
     */
    public static final String[] OPTIONS_COMBOBOX_LOGIN = {"User Login", "Employee Login", "User Sign-Up"};

    private Constants() {
    }


}

