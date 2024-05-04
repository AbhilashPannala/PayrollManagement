package Models;

import static Controllers.PayrollController.round;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PayrollRecord {

        public final SimpleStringProperty nameColumn, idColumn, grossColumn, basicColumn, houseRentColumn, medicalColumn, 
                                          perDayColumn, perHourColumn, overTimeColumn, oTPColumn, payableColumn;

        public PayrollRecord(String id, String name, String gross, String overtime){
            this.idColumn = new SimpleStringProperty(id);
            this.nameColumn = new SimpleStringProperty(name);
            this.grossColumn = new SimpleStringProperty(gross);
            this.overTimeColumn = new SimpleStringProperty(overtime);

            double basic = (Double.parseDouble(grossColumn.get()) / 100) * 60; // Basic = 60% of Gross
            basic = round(basic, 2);
            this.basicColumn = new SimpleStringProperty(String.valueOf(basic));

            double house_rent = (Double.parseDouble(grossColumn.get()) / 100) * 30; // House Rent = 30% of Gross
            house_rent = round(house_rent, 2);
            this.houseRentColumn = new SimpleStringProperty(String.valueOf(house_rent));

            double medical = (Double.parseDouble(grossColumn.get()) / 100) * 10; // Medical = 10% of Gross
            medical = round(medical, 2);
            this.medicalColumn = new SimpleStringProperty(String.valueOf(medical));

            double per_day = basic / 26; // Per Day = Basic / 26
            per_day = round(per_day, 2);
            this.perDayColumn = new SimpleStringProperty(String.valueOf(per_day));

            double per_hour = per_day / 8; // Per Hour = Per Day / 8
            per_hour = round(per_hour, 2);
            this.perHourColumn = new SimpleStringProperty(String.valueOf(per_hour));

            double over_time_pay = Double.parseDouble(overTimeColumn.get()) * per_hour * 2; // Over Time Pay = Over Time * Per Hour Pay * 2
            over_time_pay = round(over_time_pay, 2);
            this.oTPColumn = new SimpleStringProperty(String.valueOf(over_time_pay));

            double payable = basic + over_time_pay; // Payable = Basic + Over Time Pay
            payable = round(payable, 2);
            this.payableColumn = new SimpleStringProperty(String.valueOf(payable));
        }
        
        public final String getId() { return idColumn.getValue(); }
        public final String getName() { return nameColumn.getValue(); }
        public final String getGross() { return grossColumn.getValue(); }
        public final String getOverTime() { return overTimeColumn.getValue(); }
        
        public final StringProperty idProperty() { return idColumn; }
        public final StringProperty nameProperty() { return nameColumn; }
        public final StringProperty grossProperty() { return grossColumn; }
        public final StringProperty basicProperty() { return basicColumn; }
        public final StringProperty house_rentProperty() { return houseRentColumn; }
        public final StringProperty medicalProperty() { return medicalColumn; }
        public final StringProperty per_dayProperty() { return perDayColumn; }
        public final StringProperty per_hourProperty() { return perHourColumn; }
        public final StringProperty over_timeProperty() { return overTimeColumn; }
        public final StringProperty over_time_payProperty() { return oTPColumn; }
        public final StringProperty payableProperty() { return payableColumn; }
} 

