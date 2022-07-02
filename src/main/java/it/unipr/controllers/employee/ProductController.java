package it.unipr.controllers.employee;

import it.unipr.utilities.MyListener;
import it.unipr.warehouse.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * This class provides all the necessary methods to fill and manage a product row in the employee interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class ProductController {
    @FXML
    private Button btnRemoveProduct;
    @FXML
    private Label id, name, brand, price, quantity;

    private Product product;
    private MyListener myListener;

    @FXML //ActionEvent that deletes a product
    private void deleteProduct(ActionEvent actionEvent) {
        this.myListener.clickDeleteProduct(this.product.getId());
    }

    /**
     * This method sets the data for a product row
     *
     * @param p instance of {@code Product}
     * @param quantity quantity of the product available
     * @param myListener check out the see also section
     * @param labelDelete delete label used to launch an event on action
     * @param isAdmin used to distinguish if the active user is an employee or an admin
     * @see MyListener
     * @since 2.0
     */
    public void setData(Product p, int quantity, MyListener myListener, Label labelDelete, boolean isAdmin) {
        this.product = p;
        this.myListener = myListener;

        this.id.setText(p.getId());
        this.name.setText(p.getName());
        this.brand.setText(p.getBrand());
        this.price.setText(Float.toString(p.getPrice()));
        this.quantity.setText(Integer.toString(quantity));
        this.btnRemoveProduct.setDisable(!isAdmin);
    }
}
