package it.unipr.controllers.user;

import it.unipr.utilities.MyListener;
import it.unipr.warehouse.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * This class manages the data contained in the product cards of the user interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class CardController {

    @FXML
    private Label productBrand, productPrice, productName, productId;
    @FXML
    private ImageView img;

    private Product product;
    private int quantity;
    private MyListener myListener;

    @FXML //MouseEvent that shows a bigger version of the product card
    private void click(MouseEvent mouseEvent) {
        this.myListener.clickShowProduct(this.product, this.quantity);
    }

    /**
     * This method is used to set the data of the card object
     *
     * @param product used to instantiate the product attribute
     * @param quantity  used to set the quantity attribute
     * @param myListener used to set the listener attribute of the class
     * @since 2.0
     */
    public void setData(Product product, int quantity, MyListener myListener) {
        this.product = product;
        this.quantity = quantity;
        this.myListener = myListener;

        productId.setText(product.getId());
        productName.setText(product.getName());
        productBrand.setText(product.getBrand());
        productPrice.setText("€ " + product.getPrice());
    }
}
