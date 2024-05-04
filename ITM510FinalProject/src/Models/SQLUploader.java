package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUploader {

    public static void main(String[] args) {
    	
//    	To run the Payroll.sql file uncomment the below lines.
//    	or use the already existing users in the database
    	
//        try {
//            Connection conn = DatabaseConnection.getConnection();
//            Statement stmt = conn.createStatement();
//
//            String sqlFile = "Payroll.sql"; 
//            StringBuilder sqlCommands = new StringBuilder();
//            try (BufferedReader reader = new BufferedReader(new FileReader(sqlFile))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    sqlCommands.append(line);
//                    sqlCommands.append("\n");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            // Execute SQL commands
//            String[] commands = sqlCommands.toString().split(";");
//            for (String command : commands) {
//                if (!command.trim().isEmpty()) {
//                    stmt.executeUpdate(command);
//                }
//            }
//
//            System.out.println("SQL file uploaded successfully.");
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}

