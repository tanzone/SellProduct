package it.unipr.controllers.employee;

import it.unipr.utilities.MyListener;
import it.unipr.warehouse.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class provides all the necessary methods to fill and manage an order row in the employee interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 2.0
 * @since 2.0
 */
public class OrderController {
    @FXML
    private Label idOrder, username, idProduct, name, brand, quantity, totPrice;

    private Order order;
    private MyListener myListener;

    @FXML //ActionEvent that deletes an order
    private void deleteOrder(ActionEvent actionEvent) {
        this.myListener.clickDeleteOrder(this.order);
    }

    /**
     * This method sets the data for an order row
     *
     * @param o instance of {@code Order}
     * @param myListener check out the see also section
     * @see MyListener
     * @since 2.0
     */
    public void setData(Order o, MyListener myListener) {
        this.order = o;
        this.myListener = myListener;

        this.idOrder.setText(o.getOrderId());
        this.username.setText(o.getUserId());
        this.idProduct.setText(o.getId());
        this.name.setText(o.getName());
        this.brand.setText(o.getBrand());
        this.quantity.setText(Float.toString(o.getQuantity()));
        this.totPrice.setText(Float.toString(o.totalPrice()));
    }
}
