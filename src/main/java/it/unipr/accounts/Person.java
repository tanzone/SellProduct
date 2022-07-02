package it.unipr.accounts;

import java.util.Objects;

/**
 * The {@code Person}, as a parent class, provides the elementary methods and attributes for its child classes
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see Admin
 * @see Employee
 * @see User
 * <p>
 * Basically it contains the set and get methods of the attributes and
 * the two override methods for equals and toString, used for many condition checks and prints
 * @since 1.0
 */
public class Person {
    private String username;
    private String password;

    /**
     * Class default constructor
     *
     * @since 1.0
     */
    public Person() {
    }

    /**
     * Class constructor with parameters
     *
     * @param username attribute that contains the value of the username for this person
     * @param password attribute that contains the value of the password for this person
     * @since 1.0
     */
    public Person(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Class copy constructor
     *
     * @param o copy of instance of this object to create a new Person
     * @since 1.0
     */
    public Person(Person o) {
        this.username = o.getUsername();
        this.password = o.getPassword();
    }

    /**
     * Getter of the username attribute
     *
     * @return {@code String} - value of username attribute
     * @since 1.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username attribute
     *
     * @param username new value of username attribute
     * @since 1.0
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the password attribute
     *
     * @return {@code String} - value of password attribute
     * @since 1.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password attribute
     *
     * @param password new value of password attribute
     * @since 1.0
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Override method that compares objects by their attributes
     *
     * @param o Person to compare
     * @return {@code boolean} - true if the attributes are equals, false instead
     * @since 1.0
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username) && Objects.equals(password, person.password);
    }


    /**
     * Override method used to print object attributes
     *
     * @return {@code String} - string to print with custom format of object's description
     * @since 1.0
     */
    @Override
    public String toString() {
        return "Person { username = '" + username + "' --- password = '" + password + "' }";
    }
}
