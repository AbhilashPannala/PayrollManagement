package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.scene.control.Alert;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510labs?autoReconnect=true&useSSL=false";
    private static final String USER = "db510";
    private static final String PASSWORD = "510";
    static Connection conn = null;

    public static Connection getConnection() {
        try {
        	conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException e) {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database");
            alert.setHeaderText("Connection Error");
            alert.setContentText("Cannot Connect To Database!");
            alert.showAndWait();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}

