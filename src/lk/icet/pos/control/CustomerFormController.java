package lk.icet.pos.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.icet.pos.db.Database;
import lk.icet.pos.entity.Customer;
import lk.icet.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.util.Optional;

public class CustomerFormController {

    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtSearchCustomer;
    public Button btnSaveCustomer;
    public TableView<CustomerTM> tblView;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOption;
    public AnchorPane customerFormPane;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadDetails();

        tblView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData(newValue);
                btnSaveCustomer.setText("Update Customer");
            }
        }));
    }

    private void setData(CustomerTM newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }


    private void loadDetails() {
        ObservableList<CustomerTM> TMList= FXCollections.observableArrayList();
        for (Customer c:Database.customers){
            Button btn=new Button("Delete");
            CustomerTM customerTM=new CustomerTM(c.getId(),c.getName(),c.getAddress(),c.getSalary(),btn);
            TMList.add(customerTM);

            btn.setOnAction(event -> {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION,
                        "Are You Sure ?", ButtonType.NO, ButtonType.YES).showAndWait();

                if (buttonType.get()==ButtonType.YES){
                    Database.customers.remove(c);
                    loadDetails();
                    clearTexts();
                }

            });

        }
        tblView.setItems(TMList);

    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        btnSaveCustomer.setText("Save Customer");
        clearTexts();
        txtId.requestFocus();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void txtNameOnAction(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void txtxAddressOnAction(ActionEvent actionEvent) {
        txtSalary.requestFocus();
    }

    public void txtxSalaryOnAction(ActionEvent actionEvent) {
        btnSaveUpdate(actionEvent);
        txtId.requestFocus();
    }

    public void txtSearchCustomerOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveUpdate(ActionEvent actionEvent) {
        Customer newCustomer=new Customer(txtId.getText(),txtName.getText(),txtAddress.getText(),Double.parseDouble(txtSalary.getText()));
        if (btnSaveCustomer.getText().equals("Save Customer")){
            Database.customers.add(newCustomer);
            new Alert(Alert.AlertType.INFORMATION,"Customer Saved !").show();
            clearTexts();
            loadDetails();
        }else {
            for (Customer c:Database.customers) {
                if (c.getId().equals(txtId.getText())){
                    c.setName(txtName.getText());
                    c.setAddress(txtAddress.getText());
                    c.setSalary(Double.parseDouble(txtSalary.getText()));
                    new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated !").show();
                    loadDetails();
                }
            }
            clearTexts();
            btnSaveCustomer.setText("Save Customer");
        }
    }

    private void clearTexts() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }

    public void backButtonOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) customerFormPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}
