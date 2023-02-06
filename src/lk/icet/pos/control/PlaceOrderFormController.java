package lk.icet.pos.control;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.icet.pos.db.Database;
import lk.icet.pos.entity.Customer;
import lk.icet.pos.entity.Item;
import lk.icet.pos.entity.Order;
import lk.icet.pos.entity.OrderDetails;
import lk.icet.pos.view.tm.CartTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class PlaceOrderFormController {

    public AnchorPane placeOrderFormPane;
    public Button btnBackToHome;
    public ComboBox<String> cmbCustomer;
    public ComboBox<String> cmbItemCode;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtUnitPrice;
    public TextField txtQTYOnHand;
    public TextField txtRequestQuntity;
    public Button btnAddCart;
    public Button btnRemoveCart;
    public TableView<CartTM> tblCart;
    public TableColumn colItem;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQTY;
    public TableColumn colTotal;
    public TableColumn colOption;
    public Label lblTotal;
    public Button btnPlaceOrder;
    public Text txtOrderId;

    public void initialize(){

        colItem.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadCustomerIds();
        loadItemCodes();
        loadOrderid();

        cmbCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setCustomerdata(newValue);     // public ComboBox<String> cmbCustomer;
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setItemData(newValue);
            }
        });
    }

    private void loadOrderid() {
        if (Database.orders.size()>0){
            Order or=Database.orders.get(Database.orders.size()-1);
            String lastOrderId=or.getOrderId();
            String idCode=lastOrderId.split("[\\W]")[1];
            int itcode=Integer.parseInt(idCode);
            itcode++;
            txtOrderId.setText(String.format("C-%03d",itcode));

        }else{
            txtOrderId.setText("C-001");
        }

    }

    private void loadItemCodes() {
        for (Item itm:Database.items) {
            cmbItemCode.getItems().add(itm.getCode());
        }
    }

    private void setItemData(String newValue) {
        Item seletcetdItem = Database.items.stream().filter(item -> item.getCode().equals(newValue)).findFirst().orElse(null);
        txtDescription.setText(seletcetdItem.getDescription());
        txtQTYOnHand.setText(String.valueOf(seletcetdItem.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(seletcetdItem.getUnitPrice()));
    }

    private void setCustomerdata(String newValue) {
        Customer selectedCustomer = Database.customers.stream().
                filter(customer -> customer.getId().equals(newValue)).findFirst().orElse(null);
        txtName.setText(selectedCustomer.getName());
        txtAddress.setText(selectedCustomer.getAddress());
        txtSalary.setText(String.valueOf(selectedCustomer.getSalary()));
    }

    private void loadCustomerIds() {
        for (Customer c: Database.customers) {
            cmbCustomer.getItems().add(c.getId());
        }
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        ArrayList<OrderDetails> orderDetails=new ArrayList<>();
        for (CartTM cTM:itmList) {
            OrderDetails oneOrder = new OrderDetails(cTM.getItemId(), cTM.getUnitPrice(), cTM.getQTY());
            orderDetails.add(oneOrder);
            manageQTY(cTM.getItemId(),cTM.getQTY());
        }
        Order order = new Order(txtOrderId.getText(), cmbCustomer.getValue(), new Date(), Double.parseDouble(lblTotal.getText()), orderDetails);
        Database.orders.add(order);
        textClearAll();
        itmList.clear();
        lblTotal.setText(String.valueOf(0));
        loadOrderid();
    }

    private void manageQTY(String itmId,int requsetQTY) {
        for (Item itm:Database.items) {
            if (itm.getCode().equals(itmId)){
                itm.setQtyOnHand(itm.getQtyOnHand()-requsetQTY);
                return;
            }
        }
    }

    public void btnRemoveCartOnAction(ActionEvent actionEvent) {
    }
    ObservableList<CartTM> itmList= FXCollections.observableArrayList();
    public void btnAddcartOnAction(ActionEvent actionEvent) {
        double unitPrice=Double.parseDouble(txtUnitPrice.getText());
        int qty=Integer.parseInt(txtRequestQuntity.getText());
        double total=unitPrice*qty;

        if (isExistsCartTm(cmbItemCode.getValue())){
            for (CartTM ctm:tblCart.getItems()) {
                if (cmbItemCode.getValue().equals(ctm.getItemId())){
                    ctm.setQTY(qty+ctm.getQTY());
                    ctm.setTotal(total+ctm.getTotal());
                    tblCart.refresh();
                }
            }
        }else {
            Button btn=new Button("Delete");
            CartTM iTM=new CartTM(cmbItemCode.getValue(),txtDescription.getText(),unitPrice,qty,total,btn);

            btn.setOnAction(event -> {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?",
                        ButtonType.YES, ButtonType.NO).showAndWait();
                if (buttonType.get().equals(ButtonType.YES)){
                    itmList.remove(iTM);
                    calculateTotal();
                }
            });

            itmList.add(iTM);
            tblCart.setItems(itmList);
        }

        textClearAll();
        calculateTotal();
    }

    private void calculateTotal() {
        double tot=0;
        for (CartTM itm:tblCart.getItems()) {
            tot+=itm.getTotal();
        }
        lblTotal.setText(String.valueOf(tot));
    }

    private boolean isExistsCartTm(String itemId) {
        Optional<CartTM> selectCartTm = tblCart.getItems().stream().filter(o -> o.getItemId().equals(itemId)).findFirst();
        return selectCartTm.isPresent();
    }

    private void textClearAll() {
        cmbItemCode.setValue(null);
        cmbCustomer.setValue(null);
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQTYOnHand.clear();
        txtRequestQuntity.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void btnBackToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) placeOrderFormPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/dashboardForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}
