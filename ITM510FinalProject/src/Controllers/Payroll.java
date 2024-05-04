package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

import Models.DatabaseConnection;

public class Payroll extends Application {
      
    static Connection conn = DatabaseConnection.getConnection();
    
    public Payroll() {
        new DatabaseConnection();
		conn = DatabaseConnection.getConnection();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Payroll Manager Application");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}