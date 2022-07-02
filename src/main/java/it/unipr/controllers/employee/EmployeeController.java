package it.unipr.controllers.employee;

import it.unipr.accounts.Admin;
import it.unipr.accounts.Employee;
import it.unipr.accounts.Person;
import it.unipr.controllers.login.LoginController;
import it.unipr.filemanager.FileManager;
import it.unipr.utilities.MyListener;
import it.unipr.utilities.Utilities;
import it.unipr.warehouse.Order;
import it.unipr.warehouse.Product;
import it.unipr.warehouse.Warehouse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static it.unipr.utilities.Constants.*;

/**
 * This class provides all the necessary methods to manage the employee interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class EmployeeController implements Initializable {
    @FXML
    private VBox add, replenish;
    @FXML
    private ToggleButton toggleBtnAdd, toggleBtnReplenish, toggleBtnShowOutOfStock;
    @FXML
    private Label labelDelete;
    @FXML
    private TextField txtProductReplenish, txtQuantityReplenish, txtQuantityReplenishAll, txtQuantityReplenishAllOutOfStock;
    @FXML
    private TextField txtProductWarehouse, txtNameWarehouse, txtBrandWarehouse, txtPriceWarehouse, txtQuantityWarehouse;
    @FXML
    private TextField txtUsernameUser, txtPasswordUser, txtUsernameEmpl, txtPasswordEmpl;
    @FXML
    private Button btnManageOrders, btnManageWarehouse, btnManageUsers, btnManageEmployees;
    @FXML
    private TextField txtUsernameOrder, txtProductOrder;
    @FXML
    private VBox actionOrder, actionWarehouse, actionUser, actionEmployee;
    @FXML
    private HBox headerOrder, headerPerson, headerWarehouse;
    @FXML
    private Label employeeUsername;
    @FXML
    private GridPane grid;

    private Warehouse warehouse;
    private Person employee;
    private MyListener myListener;


    /**
     * This method sets the data of the employee or admin in session
     *
     * @param p instance of {@code Person}
     * @param isAdmin used to know if the user in session is an admin or not
     */
    public void setData(Person p, boolean isAdmin) {
        if (isAdmin)
            this.employee = new Admin(p);
        else {
            this.employee = new Employee(p);
            this.toggleBtnAdd.setDisable(true);
            this.btnManageEmployees.setDisable(true);
            this.btnManageUsers.setDisable(true);
        }

        this.employeeUsername.setText(p.getUsername());
    }

    //Method that sets the listener for the buttons in the scene
    private MyListener setListener() {
        return new MyListener() {
            @Override
            public void clickShowProduct(Product product, int quantity) {
            }

            @Override
            public void clickDeleteUser(String username) {
                deleteUser(username);
            }

            @Override
            public void clickDeleteEmployee(String username) {
                deleteEmployee(username);
            }

            @Override
            public void clickDeleteProduct(String id) {
                deleteProduct(id);
            }

            @Override
            public void clickDeleteOrder(Order o) {
                deleteOrder(o);
            }
        };
    }

    //Method that deletes a user using the username as parameter
    private void deleteUser(String username) {
        ((Admin) this.employee).removeUser(username);
        this.fillGridList(FileManager.readData(USER_SEL, Person.class), "ItemPerson", USER_SEL, Person.class);
    }

    //Method that deletes an employee using the username as parameter
    private void deleteEmployee(String username) {
        ((Admin) this.employee).removeEmployee(username);
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.remove(0);
        this.fillGridList(list, "ItemPerson", EMPLOYEES_SEL, Person.class);
    }

    //Method that deletes a product using the ProductId as a parameter
    private void deleteProduct(String id) {
        ((Admin) this.employee).removeProduct(id, this.warehouse);
        this.fillGridProduct(this.warehouse.getStorage());
    }

    //Method that deletes and order using an instance of order as a parameter
    private void deleteOrder(Order o) {
        ((Employee) this.employee).shipOrder(o.getOrderId(), ORDERS, this.warehouse);
        this.fillGridList(FileManager.readData(ORDERS_SEL, Order.class), "ItemOrder", ORDERS_SEL, Order.class);
    }

    //Method that fills the grid with objects based on what it needs to be shown, users, orders, employees
    private <T> void fillGridList(List<T> toShow, String file, int type, Class<T> c) {
        int row = 1;

        this.grid.getChildren().clear();

        try {
            for (T obj : toShow) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(EmployeeController.class.getResource(file + ".fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                if (file.equals("ItemPerson")) {
                    PersonController itemController = fxmlLoader.getController();
                    itemController.setData((Person) obj, type, this.myListener);
                } else {
                    OrderController itemController = fxmlLoader.getController();
                    itemController.setData((Order) obj, this.myListener);
                }

                this.setGridPosition(anchorPane, row++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method that fills the grid with products
    private void fillGridProduct(HashMap<Product, Integer> toShow) {
        int row = 1;

        this.grid.getChildren().clear();

        try {
            for (Map.Entry<Product, Integer> entry : toShow.entrySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(EmployeeController.class.getResource("ItemProduct.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController itemController = fxmlLoader.getController();
                itemController.setData(entry.getKey(), entry.getValue(), this.myListener, this.labelDelete, this.employee instanceof Admin);


                this.setGridPosition(anchorPane, row++);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method that sets the position of the grid on the screen
    private void setGridPosition(AnchorPane anchorPane, int row) {
        this.grid.add(anchorPane, 0, row); //(child,column,row)
        //set grid width
        this.grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        this.grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        this.grid.setMaxWidth(Region.USE_PREF_SIZE);

        //set grid height
        this.grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        this.grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        this.grid.setMaxHeight(Region.USE_PREF_SIZE);

        GridPane.setMargin(anchorPane, new Insets(5, 0, 0, 0));
    }

    //Method that sets the visibility of the headers of the grid
    private void setHeaders(HBox headerToShow, VBox actionToShow) {
        this.headerOrder.setVisible(false);
        this.headerPerson.setVisible(false);
        this.headerWarehouse.setVisible(false);
        this.actionOrder.setVisible(false);
        this.actionWarehouse.setVisible(false);
        this.actionUser.setVisible(false);
        this.actionEmployee.setVisible(false);

        headerToShow.setVisible(true);
        actionToShow.setVisible(true);
    }


    @FXML //ActionEvent that calls the methods needed to show the orders in the grid
    private void manageOrders(ActionEvent actionEvent) {
        this.setHeaders(this.headerOrder, this.actionOrder);
        this.fillGridList(FileManager.readData(ORDERS_SEL, Order.class), "ItemOrder", ORDERS_SEL, Order.class);
    }

    @FXML //ActionEvent that calls the methods needed to show the products in the grid
    private void manageWarehouse(ActionEvent actionEvent) {
        this.setHeaders(this.headerWarehouse, this.actionWarehouse);
        this.toggleBtnShowOutOfStock.setSelected(false);
        this.fillGridProduct(this.warehouse.getStorage());
    }

    @FXML //ActionEvent that calls the methods needed to show the users in the grid
    private void manageUsers(ActionEvent actionEvent) {
        this.setHeaders(this.headerPerson, this.actionUser);
        this.fillGridList(FileManager.readData(USER_SEL, Person.class), "ItemPerson", USER_SEL, Person.class);
    }

    @FXML //ActionEvent that calls the methods needed to show the employees in the grid
    private void manageEmployees(ActionEvent actionEvent) {
        this.setHeaders(this.headerPerson, this.actionEmployee);
        List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
        list.remove(0);
        this.fillGridList(list, "ItemPerson", EMPLOYEES_SEL, Person.class);
    }

    @FXML //ActionEvent that calls the methods needed to ship all orders of a specific user
    private void orderUsername(ActionEvent actionEvent) {
        ((Employee) this.employee).shipOrder(this.txtUsernameOrder.getText(), USERS, this.warehouse);
        this.fillGridList(FileManager.readData(ORDERS_SEL, Order.class), "ItemOrder", ORDERS_SEL, Order.class);
    }

    @FXML //ActionEvent that calls the methods needed to ship all orders containing the same product
    private void orderProduct(ActionEvent actionEvent) {
        ((Employee) this.employee).shipOrder(this.txtProductOrder.getText(), PRODUCTS, this.warehouse);
        this.fillGridList(FileManager.readData(ORDERS_SEL, Order.class), "ItemOrder", ORDERS_SEL, Order.class);
    }

    @FXML //ActionEvent that calls the methods needed to replenish a specific product in the warehouse
    private void replenishProduct(ActionEvent actionEvent) {
        try {
            String id = this.txtProductReplenish.getText();
            int quantity = Integer.parseInt((this.txtQuantityReplenish.getText()));

            ((Employee) this.employee).replenish(id, quantity, PRODUCTS, this.warehouse);
            this.fillGridProduct(this.warehouse.getStorage());
        } catch (Exception e) {
            Utilities.showAlert("Problem revealed!", "Quantity field is incorrect!", "Please try again with another parameters.");
        }
    }

    @FXML //ActionEvent that calls the methods needed to replenish all products in the warehouse
    private void replenishAll(ActionEvent actionEvent) {
        try {
            int quantity = Integer.parseInt((this.txtQuantityReplenishAll.getText()));
            ((Employee) this.employee).replenish("", quantity, ALL, this.warehouse);
            this.fillGridProduct(this.warehouse.getStorage());
        } catch (Exception e) {
            Utilities.showAlert("Problem revealed!", "Quantity field is incorrect!", "Please try again with another parameters.");
        }
    }

    @FXML //ActionEvent that calls the methods needed to replenish all out of stock products
    private void replenishAllOutOfStock(ActionEvent actionEvent) {
        try {
            int quantity = Integer.parseInt((this.txtQuantityReplenishAllOutOfStock.getText()));
            ((Employee) this.employee).replenish("", quantity, OUT_OF_STOCKS, this.warehouse);
            this.fillGridProduct(this.warehouse.getStorage());
        } catch (Exception e) {
            Utilities.showAlert("Problem revealed!", "Quantity field is incorrect!", "Please try again with another parameters.");
        }
    }

    @FXML //ActionEvent that calls the methods needed to add a new product to the warehouse
    private void addProduct(ActionEvent actionEvent) {
        try {
            String id = this.txtProductWarehouse.getText();
            String name = this.txtNameWarehouse.getText();
            String brand = this.txtBrandWarehouse.getText();
            float price = Float.parseFloat(this.txtPriceWarehouse.getText());
            int quantity = Integer.parseInt((this.txtQuantityWarehouse.getText()));

            if (!((Admin) this.employee).checkProductExist(id, this.warehouse)) {
                ((Admin) this.employee).addProduct(new Product(id, name, brand, price), quantity, this.warehouse);
                this.fillGridProduct(this.warehouse.getStorage());
            } else
                Utilities.showAlert("Error!", "Product already exist!", "Please try again with another ID");
        } catch (Exception e) {
            Utilities.showAlert("Problem revealed!", "Some field is incorrect!", "Please try again with another parameters.");
        }
    }

    @FXML //ActionEvent that calls the methods needed to add a new user account
    private void addUser(ActionEvent actionEvent) {
        String user = this.txtUsernameUser.getText();
        String pass = this.txtPasswordUser.getText();

        if (!((Admin) this.employee).checkUserExist(user)) {
            ((Admin) this.employee).addUser(new Person(user, pass));
            this.fillGridList(FileManager.readData(USER_SEL, Person.class), "ItemPerson", USER_SEL, Person.class);
        } else
            Utilities.showAlert("Error!", "User already exist!", "Please try again with another username");
    }

    @FXML //ActionEvent that calls the methods needed to add a new employee account
    private void addEmployee(ActionEvent actionEvent) {
        String user = this.txtUsernameEmpl.getText();
        String pass = this.txtPasswordEmpl.getText();

        if (!((Admin) this.employee).checkEmployeeExist(user)) {
            ((Admin) this.employee).addEmployee(new Person(user, pass));
            List<Person> list = FileManager.readData(EMPLOYEES_SEL, Person.class);
            list.remove(0);
            this.fillGridList(list, "ItemPerson", EMPLOYEES_SEL, Person.class);
        } else
            Utilities.showAlert("Error!", "Employee already exist!", "Please try again with another username");
    }

    @FXML //ActionEvent that sets the replenish button status
    private void toggleReplenish(ActionEvent actionEvent) {
        if (!this.toggleBtnReplenish.isSelected())
            this.toggleBtnReplenish.setSelected(true);
        else {
            this.add.setVisible(false);
            this.replenish.setVisible(true);
        }
        this.toggleBtnAdd.setSelected(false);
    }

    @FXML //ActionEvent that sets the add button status
    private void toggleAdd(ActionEvent actionEvent) {
        if (!this.toggleBtnAdd.isSelected())
            this.toggleBtnAdd.setSelected(true);
        else {
            this.add.setVisible(true);
            this.replenish.setVisible(false);
        }
        if (this.toggleBtnShowOutOfStock.isSelected())
            this.toggleBtnShowOutOfStock.fire();
        this.toggleBtnReplenish.setSelected(false);
    }

    @FXML //ActionEvent that sets the out of stock button status and fills the grid correspondingly
    private void toggleOutOfStock(ActionEvent actionEvent) {
        if (!this.toggleBtnShowOutOfStock.isSelected())
            this.fillGridProduct(this.warehouse.getStorage());
        else
            this.fillGridProduct(this.warehouse.getOutOfStocks());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.warehouse = new Warehouse(FileManager.readMap());
        this.myListener = setListener();

        this.btnManageOrders.fire();
        this.toggleBtnReplenish.setSelected(true);
    }

    @FXML //ActionEvent that logs the employee out
    private void logout(ActionEvent actionEvent) {
        Utilities.loadScene((Stage) this.employeeUsername.getScene().getWindow(), "loginView", "Login", LoginController.class);
    }
}