package lk.icet.pos.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.icet.pos.db.Database;
import lk.icet.pos.entity.Order;
import lk.icet.pos.entity.OrderDetails;
import lk.icet.pos.view.tm.OrderDetailsTM;

import java.util.Optional;

public class OrderDetailsFormController {
    public AnchorPane OrderDetailsPane;
    public TextField txtId;
    public TextField txtName;
    public TextField txtDate;
    public TextField txtTotal;
    public TableView<OrderDetailsTM> tblDetails;
    public TableColumn colItem;
    public TableColumn colDescription;
    public TableColumn colQTY;
    public TableColumn colUnitPrice;

    public void initialize(){
        colItem.setCellValueFactory(new PropertyValueFactory<>("item"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void loadOrderDetails(String orderid) {
        ObservableList<OrderDetailsTM> list= FXCollections.observableArrayList();
        for (Order or:Database.orders) {
            for (OrderDetails od:or.getProducts()) {
                list.add(new OrderDetailsTM(od.getItemCode(),
                        Database.items.stream().filter(item -> item.getCode().equals(od.getItemCode())).findFirst().get().getDescription(),
                        od.getQty(),od.getUnitPrice()));
            }
        }
        tblDetails.setItems(list);
    }

    public void setOrder(String orderid){
        Optional<Order> selectOrder = Database.orders.stream().filter(order -> order.getOrderId().equals(orderid)).findFirst();
        if (!selectOrder.isPresent()){
            new Alert(Alert.AlertType.CONFIRMATION,"Not Found").show();
            return;
        }else {
            txtId.setText(selectOrder.get().getOrderId());
            txtName.setText(Database.customers.stream().filter(customer -> customer.getId().equals(selectOrder.get().getCustomer())).findFirst().get().getName());
            txtDate.setText(String.valueOf(selectOrder.get().getDate()));
            txtTotal.setText(String.valueOf(selectOrder.get().getTotal()));

            loadOrderDetails(orderid);
        }
    }
}
