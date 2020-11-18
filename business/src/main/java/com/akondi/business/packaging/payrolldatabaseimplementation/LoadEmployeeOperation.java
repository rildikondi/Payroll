package com.akondi.business.packaging.payrolldatabaseimplementation;

import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrollimplementation.BiweeklySchedule;
import com.akondi.business.packaging.payrollimplementation.MonthlySchedule;
import com.akondi.business.packaging.payrollimplementation.WeeklySchedule;

import java.sql.Connection;
import java.sql.ResultSet;

public class LoadEmployeeOperation extends LoadOperation {
    private Employee employee;
    private Connection connection;

    public LoadEmployeeOperation(int employerId, Connection connection) {
        super(employerId, connection);
        this.connection = connection;
    }

    public void execute() {
        try {
            setTableName("Employee");
            ResultSet resultSet = loadData();
            createEmployee(resultSet);
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    public void addSchedule(String schedule) {
        switch (schedule) {
            case "weekly":
                employee.setSchedule(new WeeklySchedule());
                break;
            case "biweekly":
                employee.setSchedule(new BiweeklySchedule());
                break;
            case "monthly":
                employee.setSchedule(new MonthlySchedule());
                break;
            case "unknown":
                    break;

        }
    }

    private void addPaymentMethod(String paymentMethod) throws Exception {
        LoadPaymentMethodOperation operation = new LoadPaymentMethodOperation(employee, paymentMethod, connection);
        operation.execute();
        employee.setMethod(operation.getPaymentMethod());
    }

    private void addClassification(String paymentClassification) {
        LoadPaymentClassificationOperation operation = new LoadPaymentClassificationOperation(employee, paymentClassification, connection);
        operation.execute();
        employee.setClassification(operation.getPaymentClassification());
    }

    public void createEmployee(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            int employerId = resultSet.getInt("employerId");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            employee = new Employee(employerId, name, address);
            String schedule = resultSet.getString("paymentSchedule");
            addSchedule(schedule);
            String method = resultSet.getString("paymentMethod");
            addPaymentMethod(method);
            String classification = resultSet.getString("paymentClassification");
            addClassification(classification);
        }
    }

    public Employee getEmployee() {
        return employee;
    }
}
