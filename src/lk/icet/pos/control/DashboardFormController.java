package lk.icet.pos.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

public class DashboardFormController {

    public AnchorPane dashBoardPane;
    public Button btnCustomer;
    public Button btnItem;
    public Button btnOrder;
    public Button btnNewOrder;
    public Button btnIncome;
    public Button btnLogOut;
    public Label lblForm;
    public Label lblDateandTime;

    public void initialize(){
        managedateandTime();
    }

    private void managedateandTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar cal = Calendar.getInstance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                lblDateandTime.setText(sdf.format(cal.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setUI("CustomerForm");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get()==ButtonType.YES){
            setUI("LoginForm");
        }

    }

    public void setUI(String location) throws IOException {
        Stage stage= (Stage) dashBoardPane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUI("OrderForm");
    }

    public void btnNewOrderOnAction(ActionEvent actionEvent) throws IOException {
        setUI("PlaceOrderForm");
    }
}
