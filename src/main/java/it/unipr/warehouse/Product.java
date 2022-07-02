package it.unipr.warehouse;

import java.util.Objects;


/**
 * The {@code Product} class defines a product object:<br>
 * <p>
 * An instance of {@code Product} contains all the data that identifies the product itself,
 * you can compare two products, and get or set any of it's attributes<br>
 * <p>
 * To manage lists or maps of products refer to
 *
 * @author Tanzi Manuel, 307720
 * @author Mamone Maximiliano, 308214
 * @version 2.0
 * @see Warehouse
 * @since 1.0
 */
public class Product implements Comparable<Product> {
    private String id;
    private String name;
    private String brand;
    private float price;

    /**
     * Class default constructor
     *
     * @since 1.0
     */
    public Product() {
        this.id = "";
        this.name = "";
        this.brand = "";
        this.price = 0.0F;
    }

    /**
     * Class constructor with parameters
     *
     * @param id    parameter that contains the identifier of the product to set
     * @param name  parameter that contains the name of the product to set
     * @param brand parameter that contains the brand's name of the product to set
     * @param price parameter that contains the price of the product to set
     * @since 1.0
     */
    public Product(String id, String name, String brand, float price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    /**
     * Class copy constructor
     *
     * @param p another instance of {@code Product} used to create a copy of it
     * @since 1.0
     */
    public Product(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.brand = p.getBrand();
        this.price = p.getPrice();
    }

    /**
     * Getter of the id attribute
     *
     * @return {@code String} - the id of the product
     * @since 1.0
     */
    public String getId() {
        return id;
    }

    /**
     * Setter ot the id attribute
     *
     * @param id New id to set
     * @since 1.0
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of the name attribute
     *
     * @return {@code String} - the name of the product
     * @since 1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name New name to set
     * @since 1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the brand attribute
     *
     * @return {@code String} - the brand of the product
     * @since 1.0
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Setter of the brand attribute
     *
     * @param brand New brand to set
     * @since 1.0
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Getter of the price attribute
     *
     * @return {@code String} - the price of the product
     * @since 1.0
     */
    public float getPrice() {
        return price;
    }

    /**
     * Setter of the price attribute
     *
     * @param price New price to set
     * @since 1.0
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Method that returns true if the product is null and false if not
     *
     * @return {@code boolean} - isNull
     * @since 1.0
     */
    public boolean isNull() {
        return (this.id.equals(""));
    }

    /**
     * Method that compares two instances of {@code Product}
     *
     * @param o Another instance of {@code Product} to compare with
     * @return {@code boolean} - if they are equal to each other
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    /**
     * Method that creates a formatted {@code String} with the attributes of the product
     *
     * @return {@code String} - formatted string ready to be printed
     * @since 1.0
     */
    @Override
    public String toString() {
        return "\nProductID: " + this.id + "\nName: " + this.name + "\nBrand: " + this.brand + "\nProduct Price: " + this.price;
    }

    /**
     * Method override necessary to compare product to product
     *
     * @param o {@code Product} to compare
     * @return {@code Float.compare} -1 if minor, 0 if equal, 1 if major
     * @since 2.0
     * @see Product
     */
    @Override
    public int compareTo(Product o) {
        return Float.compare(this.price, o.getPrice());
    }
}
