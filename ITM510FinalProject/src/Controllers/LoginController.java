package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField userEmailField;

    @FXML
    private PasswordField userPasswordField;

    @FXML
    private ComboBox<String> userRoleComboBox;

    @FXML
    private Button userLoginButton;

    @FXML
    private void initialize() {
        userRoleComboBox.getItems().addAll("ADMIN", "MANAGER", "EMPLOYEE");
    }

    @FXML
    private void handleLoginButtonAction() {
        String email = userEmailField.getText();
        String password = userPasswordField.getText();
        String role = userRoleComboBox.getValue();
        
        boolean isValidUser = UserAuthentication.authenticate(email, password, role);
        if (isValidUser) {
            loadDashboard(role);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials or role. Please try again.");
            alert.showAndWait();
        }
    }

    private void loadDashboard(String role) {
        try {
            String fxmlFile = "";
            switch (role.toLowerCase()) {
                case "admin":
                    fxmlFile = "/Views/admin_dashboard.fxml";
                    break;
                case "employee":
                    fxmlFile = "/Views/employee_dashboard.fxml";
                    break;
                case "manager":
                    fxmlFile = "/Views/manager_dashboard.fxml";
                    break;
            }
            if (!fxmlFile.isEmpty()) {
                Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
                Stage stage = (Stage) userEmailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Show error or log it
        }
    }
}
