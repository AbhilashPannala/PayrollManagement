package Controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Models.PayrollRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PayrollController implements Initializable {

	@FXML
	private Button btnDelete, btnSave, btnClear, btnReport;

	@FXML
	private TextField searchField;

	@FXML
	private TextField nameField, idField, grossField, basicField, houseRentField, medicalField, perDayField,
			perHourField, overTimeField, overTimePayField, payableField;
	@FXML
	private TableView<PayrollRecord> table;

	@FXML
	private TableColumn<PayrollRecord, String> idColumn, nameColumn, grossColumn, basicColumn, houseRentColumn,
			medicalColumn, perDayColumn, perHourColumn, overTimeColumn, oTPColumn, payableColumn;

	ObservableList<PayrollRecord> data = FXCollections.observableArrayList();

	private void DisplayError(String title, String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public void calculateValues() {

		double gross, over_time;

		if (grossField.getText().equals("") || grossField.getText().equals("0")
				|| Double.parseDouble(grossField.getText()) < 0) {
			gross = 0;
		} else {
			gross = Double.parseDouble(grossField.getText());
		}

		if (overTimeField.getText().equals("") || Double.parseDouble(overTimeField.getText()) < 0) {
			over_time = 0;
		} else {
			over_time = Double.parseDouble(overTimeField.getText());
		}

		double basic = (gross / 100) * 65; // Basic = 65% of Gross
		basic = round(basic, 2);
		basicField.setText(String.valueOf(basic));

		double house_rent = (gross / 100) * 25; // House Rent = 25% of Gross
		house_rent = round(house_rent, 2);
		houseRentField.setText(String.valueOf(house_rent));

		double medical = (gross / 100) * 10; // Medical = 10% of Gross
		medical = round(medical, 2);
		medicalField.setText(String.valueOf(medical));

		double per_day = basic / 26; // Per Day = Basic / 26
		per_day = round(per_day, 2);
		perDayField.setText(String.valueOf(per_day));

		double per_hour = per_day / 8; // Per Hour = Per Day / 8
		per_hour = round(per_hour, 2);
		perHourField.setText(String.valueOf(per_hour));

		double over_time_pay = over_time * per_hour * 2; // Over Time Pay = Over Time * Per Hour Pay * 2
		over_time_pay = round(over_time_pay, 2);
		overTimePayField.setText(String.valueOf(over_time_pay));

		double payable = basic + over_time_pay; // Payable = Basic + Over Time Pay
		payable = round(payable, 2);
		payableField.setText(String.valueOf(payable));
	}

	@FXML
	public void onSaveButtonClicked(ActionEvent event) {
	    if (idField.getText().isEmpty()) {
	        DisplayError("Error", "Missing ID", "Please provide a valid ID.");
	        return;
	    }

	    if (nameField.getText().isEmpty() || nameField.getText().length() < 4) {
	        DisplayError("Error", "Invalid Name", "Please provide a valid name with at least 4 characters.");
	        return;
	    }

	    double grossSalary = parseDoubleOrDefault(grossField.getText(), 0.0);
	    double overTimeHours = parseDoubleOrDefault(overTimeField.getText(), 0.0);

	    calculateValues();

	    try {
	        PreparedStatement statement;
	        if (employeeExists(idField.getText())) {
	            // Update existing record
	            statement = Payroll.conn.prepareStatement(
	                "UPDATE payroll SET name = ?, gross = ?, over = ? WHERE id = ?");
	            statement.setString(1, nameField.getText());
	            statement.setDouble(2, grossSalary);
	            statement.setDouble(3, overTimeHours);
	            statement.setString(4, idField.getText());
	        } else {
	            // Insert new record
	            statement = Payroll.conn.prepareStatement(
	                "INSERT INTO `payroll` (`id`, `name`, `gross`, `over`) VALUES (?, ?, ?, ?)");
	            statement.setString(1, idField.getText());
	            statement.setString(2, nameField.getText());
	            statement.setDouble(3, grossSalary);
	            statement.setDouble(4, overTimeHours);
	        }

	        statement.executeUpdate();
	        displayAllRecords();
	    } catch (SQLException ex) {
	        handleSQLException(ex);
	    }

	    onButtonClear();
	}

	private boolean employeeExists(String id) throws SQLException {
	    PreparedStatement statement = Payroll.conn.prepareStatement(
	        "SELECT * FROM payroll WHERE id = ?");
	    statement.setString(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    return resultSet.next();
	}

	
	private double parseDoubleOrDefault(String text, double defaultValue) {
	    try {
	        return Double.parseDouble(text);
	    } catch (NumberFormatException e) {
	        return defaultValue;
	    }
	}

	private void handleSQLException(SQLException ex) {
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());

		DisplayError("SQL Error", "An error occurred while processing your request.",
				"Please try again later or contact support for assistance.");
	}

	@FXML
	public void reload() {
	    try (Statement stmt = Payroll.conn.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT * FROM payroll")) {

	        data.clear();

	        while (rs.next()) {
	            String id = rs.getString("id");
	            String name = rs.getString("name");
	            String gross = rs.getString("gross");
	            String over = rs.getString("over");

	            System.out.println(id + ", " + name + ", " + gross + ", " + over);

	            PayrollRecord pt = new PayrollRecord(id, name, gross, over);
	            data.add(pt);
	        }
	    } catch (SQLException ex) {
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	}

	public void onTableClick() {
	    PayrollRecord selectedItem = table.getSelectionModel().getSelectedItem();
	    if (selectedItem != null) {
	        idField.setText(selectedItem.getId());
	        nameField.setText(selectedItem.getName());
	        grossField.setText(selectedItem.getGross());
	        overTimeField.setText(selectedItem.getOverTime());
	        calculateValues();

	        btnDelete.setDisable(false);
	        btnReport.setDisable(false);
	        idField.setEditable(false);
	    }
	}


	public void logoutAction(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Views/login.fxml"));
			Parent root = fxmlLoader.load();

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onButtonClear() {
	    clearFields();
	    btnDelete.setDisable(true);
	    btnReport.setDisable(true);
	    idField.setEditable(true);
	}

	@FXML
	public void onButtonDelete() {
	    try {
	        String idToDelete = idField.getText();
	        if (!idToDelete.isEmpty()) {
	            Statement stmt = Payroll.conn.createStatement();
	            stmt.executeUpdate("DELETE FROM payroll WHERE id = '" + idToDelete + "'");
	            displayAllRecords();
	            clearFields();
	            btnDelete.setDisable(true);
	            idField.setEditable(true);
	        } else {
	            DisplayError("Error", "ID Field Empty", "Please enter an ID to delete.");
	        }
	    } catch (SQLException ex) {
	        DisplayError("Error", "SQL Exception", ex.getMessage());
	    }
	    displayAllRecords();
	}

	private void clearFields() {
	    idField.setText("");
	    nameField.setText("");
	    grossField.setText("");
	    overTimeField.setText("");
	    calculateValues();
	}

	@FXML
	public void onButtonReport(ActionEvent event) {
		String id = table.getSelectionModel().getSelectedItem().getId();
		String name = table.getSelectionModel().getSelectedItem().getName();
		String gross = table.getSelectionModel().getSelectedItem().getGross();
		String over_time = table.getSelectionModel().getSelectedItem().getOverTime();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/Views/Report.fxml"));

			ReportController controller = new ReportController(id, name, gross, over_time);

			fxmlLoader.setController(controller);
			Scene scene = new Scene(fxmlLoader.load(), 500, 400);
			Stage stage = new Stage();
			stage.setTitle("Payroll Manager - Report");
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
			System.out.println("TRYING!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void displayAllRecords() {
		try {
			Statement stmt = Payroll.conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM payroll");
			ObservableList<PayrollRecord> allData = FXCollections.observableArrayList();

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String gross = rs.getString("gross");
				String over = rs.getString("over");

				PayrollRecord pt = new PayrollRecord(id, name, gross, over);
				allData.add(pt);
			}

			table.setItems(allData);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	void handleDisplayAll(ActionEvent event) {
		displayAllRecords();
	}

	@FXML
	public void searchAction() {
		String searchText = searchField.getText().trim();

		if (searchText.isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Empty Search Field");
			alert.setHeaderText(null);
			alert.setContentText("Please enter a value in the search field.");
			alert.showAndWait();
			return;
		}

		try {
			PreparedStatement stmt = Payroll.conn.prepareStatement("SELECT * FROM payroll WHERE id = ? OR name LIKE ?");
			stmt.setString(1, searchText);
			stmt.setString(2, "%" + searchText + "%");
			ResultSet rs = stmt.executeQuery();
			ObservableList<PayrollRecord> searchData = FXCollections.observableArrayList();

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String gross = rs.getString("gross");
				String over = rs.getString("over");

				PayrollRecord pt = new PayrollRecord(id, name, gross, over);
				searchData.add(pt);
			}

			if (!searchData.isEmpty()) {
				table.setItems(searchData);
			} else {
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Search Results");
				alert.setHeaderText(null);
				alert.setContentText("No matching records found.");
				alert.showAndWait();

				displayAllRecords();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	    initializeTableColumns();
	    loadPayrollData();
	}

	private void initializeTableColumns() {
	    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	    grossColumn.setCellValueFactory(new PropertyValueFactory<>("gross"));
	    basicColumn.setCellValueFactory(new PropertyValueFactory<>("basic"));
	    houseRentColumn.setCellValueFactory(new PropertyValueFactory<>("house_rent"));
	    medicalColumn.setCellValueFactory(new PropertyValueFactory<>("medical"));
	    perDayColumn.setCellValueFactory(new PropertyValueFactory<>("per_day"));
	    perHourColumn.setCellValueFactory(new PropertyValueFactory<>("per_hour"));
	    overTimeColumn.setCellValueFactory(new PropertyValueFactory<>("over_time"));
	    oTPColumn.setCellValueFactory(new PropertyValueFactory<>("over_time_pay"));
	    payableColumn.setCellValueFactory(new PropertyValueFactory<>("payable"));
	}

	private void loadPayrollData() {
		displayAllRecords();
	}

}
