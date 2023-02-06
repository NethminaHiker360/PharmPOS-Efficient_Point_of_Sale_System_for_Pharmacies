package lk.icet.pos.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.icet.pos.db.Database;
import lk.icet.pos.entity.Customer;
import lk.icet.pos.entity.Order;
import lk.icet.pos.view.tm.OrderTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

public class OrderFormController {
    public Button btnBack;
    public TableView<OrderTM> tblOrder;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colCost;
    public TableColumn colDate;
    public AnchorPane orderFornPane;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadData();

        tblOrder.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue!=null){
                try {
                    loadDetails(newValue.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }));
    }

    private void loadDetails(String id) throws IOException {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("../view/OrderDetailsForm.fxml"));
        Parent load = loader.load();
        OrderDetailsFormController controller=loader.getController();
        controller.setOrder(id);
        Stage stage=new Stage();
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
        stage.show();
    }

    private void loadData() {
        ObservableList<OrderTM> list= FXCollections.observableArrayList();
        for (Order otm: Database.orders) {
            list.add(new OrderTM(otm.getOrderId(),
                    Database.customers.stream().filter(e -> e.getId().equals(otm.getCustomer())).findFirst().get().getName()
                    ,otm.getTotal(),
                    new SimpleDateFormat("yyyy-MM-dd").format(otm.getDate())));
        }
        tblOrder.setItems(list);
    }



    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage= (Stage) orderFornPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }
}
