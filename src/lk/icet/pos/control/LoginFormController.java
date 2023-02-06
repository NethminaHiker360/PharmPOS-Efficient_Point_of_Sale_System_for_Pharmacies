package lk.icet.pos.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.icet.pos.db.Database;
import lk.icet.pos.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUsername;
    public TextField txtPassword;
    public Button btnLogin;
    public Label lblTitle;
    public AnchorPane LoginFormContext;

    public void txtUsernameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        btnLoginOnAction();
    }

    public void btnLoginOnAction() throws IOException {
        User selectedUser = Database.Users.stream().filter
                (User -> User.getUsername().equals(txtUsername.getText()))
                .findFirst().orElse(null);
        if (selectedUser!=null){
            if (BCrypt.checkpw(txtPassword.getText(),selectedUser.getPassword())){
                Stage stage = (Stage) LoginFormContext.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
                stage.centerOnScreen();

            }else {
                new Alert(Alert.AlertType.WARNING,"Wrong Password").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"User Not Found").show();
        }

    }
}
