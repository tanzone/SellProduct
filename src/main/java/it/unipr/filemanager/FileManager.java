package it.unipr.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unipr.warehouse.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static it.unipr.utilities.Constants.*;

/**
 * The {@code FileManager} class defines a Filemanager object: <br>
 * <p>
 * An instance of {@code FileManager} contains all the necessary methods to read and write from .json files, allowing the application to retrieve its important data from them.
 * It uses for the most part generic functions, apart from the ones needed to create the {@code HashMap}s of {@code Product} and {@code Integer}
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class FileManager {
    private static String identifyNameFile(int type) {
        String name = "";
        if (type == PRODUCTS_SEL) {
            name = PRODUCTS;
        }
        if (type == USER_SEL) {
            name = USERS;
        }
        if (type == EMPLOYEES_SEL) {
            name = EMPLOYEES;
        }
        if (type == ORDERS_SEL) {
            name = ORDERS;
        }

        return DATAFILE + name + EXTENSION;
    }

    private static boolean exist(int type) {
        File f = new File(identifyNameFile(type));
        return (f.exists() && !f.isDirectory());
    }

    /**
     * Method that reads from a specific file (chosen with the {@code type} parameter), and with its content fills a list of {@code T} class objects
     *
     * @param type parameter that tells the method which file it needs to read (Products, Orders, Employees, Users)
     * @param c    parameter that contains an instance of {@code T} class object
     * @param <T>  parameter that contains the class of objects to fill the list with
     * @return {@code List<T>} - java list of {@code T} class objects
     * @since 1.0
     */
    public static <T> List<T> readData(int type, Class<T> c) {
        if (exist(type))
            try {
                Reader reader = Files.newBufferedReader(Paths.get(identifyNameFile(type)));
                List<T> list = new Gson().fromJson(reader, TypeToken.getParameterized(List.class, c).getType());
                reader.close();
                if (list == null) return new ArrayList<T>();
                return list;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Can't connect to the database!!");
            }

        return new ArrayList<T>();
    }

    /**
     * Method that writes a {@code List} of generic {@code T} class objects to specific .json file (chosen with the {@code type} parameter).
     *
     * @param list parameter that contains the list of objects to write to the file
     * @param type parameter that tells the method which file it needs to write to (Products, Orders, Employees, Users)
     * @since 1.0
     */
    public static void writeData(List<?> list, int type) {
        try {
            Writer writer = new FileWriter(identifyNameFile(type));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(list, writer);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't connect to the database!!");
        }
    }

    /**
     * Method that reads from the product file and fills a map with the {@code Product} as key and the quantity as value.
     *
     * @return {@code HashMap<Product, Integer>} - the map of products and quantity
     * @see Product
     * @since 1.0
     */
    public static HashMap<Product, Integer> readMap() {
        if (exist(PRODUCTS_SEL))
            try {
                Reader reader = Files.newBufferedReader(Paths.get(identifyNameFile(PRODUCTS_SEL)));
                HashMap<Product, Integer> productMap = new Gson().fromJson(reader, new TypeToken<HashMap<Product, Integer>>() {
                }.getType());
                reader.close();
                if (productMap == null) return new HashMap<>();
                return productMap;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Can't connect to the database!!");
            }

        return new HashMap<>();
    }

    /**
     * Method that writes a map of products and quantity to the products .json file
     *
     * @param productMap parameter that contains the {@code HashMap<Product, Integer>} to write
     * @since 1.0
     */
    public static void writeMap(HashMap<Product, Integer> productMap) {
        try {
            Writer writer = new FileWriter(identifyNameFile(PRODUCTS_SEL));
            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
            gson.toJson(productMap, writer);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't connect to the database!!");
        }
    }

    /**
     * Method that deletes the .json files containing the users, products and orders.
     *
     * @since 1.0
     */
    public static void deleteAll() {
        new File(identifyNameFile(USER_SEL)).delete();
        new File(identifyNameFile(PRODUCTS_SEL)).delete();
        new File(identifyNameFile(ORDERS_SEL)).delete();

    }
}