package com.akondi.payroll.packaging.payrolldatabaseimplementation;

import packaging.payrolldomain.Employee;
import packaging.payrolldomain.PaymentMethod;
import packaging.payrollimplementation.DirectDepositMethod;
import packaging.payrollimplementation.HoldMethod;
import packaging.payrollimplementation.MailMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoadPaymentMethodOperation {
    private final Employee employee;
    private final String methodCode;
    private Connection connection;
    public PaymentMethod paymentMethod;

    private interface PaymentMethodCreator {
        void execute(ResultSet resultSet);
    }

    private PaymentMethodCreator methodCreator;
    private String tableName;
    private ResultSet resultSet;

    public LoadPaymentMethodOperation(Employee employee, String methodCode, Connection connection) {
        this.employee = employee;
        this.methodCode = methodCode;
        this.connection = connection;
    }

    public void execute() throws Exception {
        prepare();
        resultSet = loadData();
    }

    public void createPaymentMethod(ResultSet resultSet) {
        methodCreator.execute(resultSet);
    }

    public void prepare() {
        switch (methodCode) {
            case "hold":
                methodCreator = this::createHoldMethod;
                createPaymentMethod(resultSet);
                break;
            case "direct deposit":
                tableName = "DirectDepositAccount";
                methodCreator = this::createDirectDepositMethod;
                createPaymentMethod(resultSet);
                break;
            case "mail":
                tableName = "PaycheckAddress";
                methodCreator = this::createMailMethod;
                createPaymentMethod(resultSet);
                break;
            case "unknown":
                break;
        }
    }

    private ResultSet loadData() throws Exception {
        if (tableName != null)
            return getPreparedStatement().executeQuery();
        else
            return null;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PreparedStatement getPreparedStatement() throws Exception {
        String sql = String.format("SELECT * FROM %s where employerId = ?", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employee.getEmployerId());
        return preparedStatement;
    }

    public void createDirectDepositMethod(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                String bank = resultSet.getString("bank");
                String account = resultSet.getString("account");
                paymentMethod = new DirectDepositMethod(bank, account);
            }
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private void createHoldMethod(ResultSet resultSet) {
        paymentMethod = new HoldMethod();
    }

    private void createMailMethod(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                String address = resultSet.getString("address");
                paymentMethod = new MailMethod(address);
            }
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}
