package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ReportController implements Initializable {

    private String employeeId, employeeName, grossSalary, overTimeHours;
    
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private Button printButton;
    
    @FXML
    private Label nameField, idField, grossSalaryField, basicSalaryField, houseRentField, medicalField, perDayField, perHourField, overTimeField, overTimePayField, payableField;
    
    public ReportController(String employeeId, String employeeName, String grossSalary, String overTimeHours) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.grossSalary = grossSalary;
        this.overTimeHours = overTimeHours;
    }
    
    public void calculateValues() {
        idField.setText(employeeId);
        nameField.setText(employeeName);
        grossSalaryField.setText(grossSalary);
        overTimeField.setText(overTimeHours);
        
        double grossSalaryValue = parseDoubleValue(grossSalaryField.getText());
        double overTimeHoursValue = parseDoubleValue(overTimeField.getText());
        
        double basicSalary = calculatePercentage(grossSalaryValue, 65);
        double houseRent = calculatePercentage(grossSalaryValue, 25);
        double medical = calculatePercentage(grossSalaryValue, 10);
        double perDaySalary = basicSalary / 26;
        double perHourSalary = perDaySalary / 8;
        double overTimePay = overTimeHoursValue * perHourSalary * 2;
        double payableAmount = basicSalary + overTimePay;
        
        setLabelValue(basicSalaryField, basicSalary);
        setLabelValue(houseRentField, houseRent);
        setLabelValue(medicalField, medical);
        setLabelValue(perDayField, perDaySalary);
        setLabelValue(perHourField, perHourSalary);
        setLabelValue(overTimePayField, overTimePay);
        setLabelValue(payableField, payableAmount);
        
        grossSalaryField.setText(grossSalaryValue + " $");
        overTimeField.setText(overTimeHoursValue + " Hour");
    }
    
    @FXML
    private void onButtonPrint() {
        printButton.setVisible(false);
        print(rootPane);
        printButton.setVisible(true);
    }
         
    
    private void print(Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(node.getScene().getWindow())){
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
        }
    }
    
    private double parseDoubleValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private double calculatePercentage(double value, double percentage) {
        return round((value / 100) * percentage, 2);
    }
    
    private void setLabelValue(Label label, double value) {
        label.setText(String.valueOf(value) + " $");
    }
    
    private double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        calculateValues();
    }    
    
}
