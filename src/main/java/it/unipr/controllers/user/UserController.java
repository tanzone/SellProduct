package it.unipr.controllers.user;


import it.unipr.accounts.Person;
import it.unipr.accounts.User;
import it.unipr.controllers.login.LoginController;
import it.unipr.filemanager.FileManager;
import it.unipr.utilities.MyListener;
import it.unipr.utilities.Utilities;
import it.unipr.warehouse.Order;
import it.unipr.warehouse.Product;
import it.unipr.warehouse.Warehouse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static it.unipr.utilities.Constants.FILTERMAP;

/**
 * This class provides all the necessary methods to manage the user interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class UserController implements Initializable {
    @FXML
    private Label username;
    @FXML
    private TextField textSearch, txtPriceFilter;
    @FXML
    private ToggleButton btnId, btnName, btnBrand;
    @FXML
    private ToggleButton[] btnFilter;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private Label textProdName, textProdBrand, textProdPrice, textProdQuantity, textOrderTot;
    @FXML
    private Spinner<Integer> textBuyQuantity;
    @FXML
    private ToggleButton btnOrders;
    @FXML
    private BorderPane viewOrder;
    @FXML
    private GridPane grid;
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private List<TableColumn<Object, Object>> columnsTable;

    private Warehouse warehouse;
    private User user;
    private Product cardProduct;
    private MyListener myListener;

    /**
     * This method is used to initialize the username and the instance of the {@code User} object
     *
     * @param p this parameter contains the {@code Person} instance used to create the attributes
     */
    public void setData(Person p) {
        this.user = new User(p);
        this.username.setText(p.getUsername());
    }

    //Method that sets the values of the focused product card
    private void showProduct(Product product, int quantity) {
        this.cardProduct = product;
        this.chosenFruitCard.setVisible(true);
        this.textProdName.setText(product.getName());
        this.textProdBrand.setText(product.getBrand());
        this.textProdPrice.setText("€ " + product.getPrice());
        this.textProdQuantity.setText(Integer.toString(quantity));
        this.textBuyQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, quantity, 1));
        this.textOrderTot.setText("" + this.warehouse.totalPriceOrder(product.getId(), 1));
    }

    //Listener for the various actions
    private MyListener setListener() {
        return new MyListener() {
            @Override
            public void clickShowProduct(Product product, int quantity) {
                showProduct(product, quantity);
            }

            @Override
            public void clickDeleteUser(String username) {
            }

            @Override
            public void clickDeleteEmployee(String username) {
            }

            @Override
            public void clickDeleteProduct(String id) {
            }

            @Override
            public void clickDeleteOrder(Order o) {
            }
        };
    }

    //Method that fills the grid with products
    private void fillGrid(HashMap<Product, Integer> products) {
        int column = 0;
        int row = 1;

        this.grid.getChildren().clear();

        try {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                FXMLLoader fxmlLoader = new FXMLLoader(UserController.class.getResource("itemCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardController itemController = fxmlLoader.getController();
                itemController.setData(entry.getKey(), entry.getValue(), this.myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method that creates a listener needed to check if the spinner contains acceptable values and, if not, corrects it
    private void checkSpinner() throws Exception {
        this.textBuyQuantity.getEditor().textProperty().addListener((obs, oldval, newval) -> {

            SpinnerValueFactory<Integer> valueFactory = this.textBuyQuantity.getValueFactory();
            if (valueFactory != null) {
                StringConverter<Integer> converter = valueFactory.getConverter();
                if (converter != null) {
                    try {
                        Integer value = 1;
                        if (!newval.isBlank())
                            value = converter.fromString(newval);
                        if (value != null)
                            valueFactory.setValue(value);
                        else {
                            valueFactory.setValue(1);
                            this.textBuyQuantity.getEditor().setText("");
                        }
                        this.textOrderTot.setText("" + this.warehouse.totalPriceOrder(this.cardProduct.getId(), valueFactory.getValue()));

                    } catch (NullPointerException e) {
                        System.out.println("Error");
                    } catch (Exception ex) {
                        valueFactory.setValue(1);
                        this.textBuyQuantity.getEditor().setText(converter.toString(valueFactory.getValue()));
                        this.textOrderTot.setText("" + this.warehouse.totalPriceOrder(this.cardProduct.getId(), valueFactory.getValue()));
                    }
                }
            }
        });
    }

    //Method that sets the filtering method
    private void setFilter() {
        String[] promptFilter = {"Id ", "Name ", "Brand"};
        StringBuilder toPrint = new StringBuilder();

        for (int i = 0; i < promptFilter.length; i++)
            if (this.btnFilter[i].isSelected())
                toPrint.append(promptFilter[i]);

        if (toPrint.isEmpty()) this.textSearch.setPromptText("Select type for filter");
        else this.textSearch.setPromptText(toPrint.toString());
    }

    //Method that sets the order table
    private void setTable(List<Order> orders) {
        ObservableList<Order> ordersToPrint = FXCollections.observableArrayList(orders);
        this.orderTable.setItems(ordersToPrint);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.warehouse = new Warehouse(FileManager.readMap());
        this.btnFilter = new ToggleButton[]{btnId, btnName, btnBrand};
        this.btnFilter[0].setSelected(true);
        this.chosenFruitCard.setVisible(false);
        this.myListener = this.setListener();

        this.fillGrid(this.warehouse.getStorage());
        try {
            this.checkSpinner();
        } catch (Exception e) {
            System.out.println("Niente di che");
        }


        String[] strCol = {"orderId", "userId", "id", "name", "brand", "price", "quantity"};
        for (int i = 0; i < strCol.length; i++)
            this.columnsTable.get(i).setCellValueFactory(new PropertyValueFactory<>(strCol[i]));
    }

    @FXML //ActionEvent that sets a specific filter
    private void btnFilter(ActionEvent actionEvent) {
        this.setFilter();
    }

    @FXML //ActionEvent that launches a search in the product list based on the filtering choices of the user
    private void search(ActionEvent actionEvent) {
        if (!this.textSearch.getText().equals("")) {
            this.warehouse.resetFilter();
            String[] toSearch = (this.textSearch.getText().toLowerCase() + " - - -").split(" ");

            if (this.btnFilter[0].isSelected() && !toSearch[0].equals("-"))
                this.warehouse.addToMap(FILTERMAP[0], toSearch[0]);

            if (!this.btnFilter[0].isSelected() && this.btnFilter[1].isSelected() && !toSearch[0].equals("-"))
                this.warehouse.addToMap(FILTERMAP[1], toSearch[0]);

            if (this.btnFilter[0].isSelected() && this.btnFilter[1].isSelected() && !toSearch[1].equals("-"))
                this.warehouse.addToMap(FILTERMAP[1], toSearch[1]);

            if (!this.btnFilter[0].isSelected() && !this.btnFilter[1].isSelected() && this.btnFilter[2].isSelected() && !toSearch[0].equals("-"))
                this.warehouse.addToMap(FILTERMAP[2], toSearch[0]);

            if (this.btnFilter[0].isSelected() && !this.btnFilter[1].isSelected() && this.btnFilter[2].isSelected() && !toSearch[1].equals("-"))
                this.warehouse.addToMap(FILTERMAP[2], toSearch[1]);

            if (!this.btnFilter[0].isSelected() && this.btnFilter[1].isSelected() && this.btnFilter[2].isSelected() && !toSearch[1].equals("-"))
                this.warehouse.addToMap(FILTERMAP[2], toSearch[1]);

            if (this.btnFilter[0].isSelected() && this.btnFilter[1].isSelected() && this.btnFilter[2].isSelected() && !toSearch[2].equals("-"))
                this.warehouse.addToMap(FILTERMAP[2], toSearch[2]);

            this.fillGrid(this.warehouse.filterProduct());
        } else
            this.fillGrid(this.warehouse.getStorage());
    }

    @FXML //ActionEvent that checks all the requirements before buying a certain product in a certain quantity
    private void buy(ActionEvent actionEvent) {
        try {
            int quantityBought = this.textBuyQuantity.getValue();
            if (quantityBought <= Integer.parseInt(this.textProdQuantity.getText())) {
                this.user.addBoughtOrder(this.warehouse.buyProduct(this.cardProduct.getId(), this.textBuyQuantity.getValue(), this.user.getUsername()));

                int newQuantity = Integer.parseInt(this.textProdQuantity.getText()) - this.textBuyQuantity.getValue();
                this.textProdQuantity.setText("" + newQuantity);
                this.textBuyQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newQuantity + 1, 1));
                this.fillGrid(this.warehouse.getStorage());

                Utilities.showAlert("Order Placed", "Order Placed Successfully!", this.cardProduct.toString() + "\nQuantity: " + quantityBought);
            } else
                Utilities.showAlert("Order Placed", "Order Placed Failed!", "Error!");
        } catch (Exception e) {
            Utilities.showAlert("Order Placed", "Order Placed Failed!", "Error!");
        }

    }

    @FXML //ActionEvent that changes from products' list to orders' list
    private void lookOrders(ActionEvent actionEvent) {
        this.viewOrder.setVisible(this.btnOrders.isSelected());
        if (this.btnOrders.isSelected())
            this.setTable(this.user.getAllOrders());
    }

    @FXML //ActionEvent that fills the list with the in session's orders
    private void btnSessionOrders(ActionEvent actionEvent) {
        this.setTable(this.user.getBoughtOrder());
    }

    @FXML //ActionEvent that filss the list with all the user orders
    private void btnAllOrders(ActionEvent actionEvent) {
        this.setTable(this.user.getAllOrders());
    }

    @FXML //ActionEvent that logs the user out
    private void logout(ActionEvent actionEvent) {
        Utilities.loadScene((Stage) this.textSearch.getScene().getWindow(), "loginView", "Login", LoginController.class);
    }

    //Method that handles the filtering by price of the products
    private void priceFilter(int actionFilter, int otherFilter, boolean typeFiler) {
        this.warehouse.addToMap(FILTERMAP[actionFilter], "");
        this.warehouse.addToMap(FILTERMAP[otherFilter], "");
        if (this.txtPriceFilter.getText().equals(""))
            this.fillGrid(this.warehouse.orderMap(typeFiler, this.warehouse.filterProduct()));
        else {
            try {
                this.warehouse.addToMap(FILTERMAP[actionFilter], this.txtPriceFilter.getText());
                this.fillGrid(this.warehouse.orderMap(typeFiler, this.warehouse.filterProduct()));
            } catch (Exception e) {
                Utilities.showAlert("Price filter error", "Wrong value!", "Please try again with another value!");
                this.txtPriceFilter.setText("");
            }
        }
    }

    @FXML //ActionEvent that sets the status of the min price filtering button
    private void minFilter(ActionEvent actionEvent) {
        this.priceFilter(3, 4, false);
    }

    @FXML //ActionEvent that sets the status of the max price filtering button
    private void maxFilter(ActionEvent actionEvent) {
        this.priceFilter(4, 3, true);
    }
}