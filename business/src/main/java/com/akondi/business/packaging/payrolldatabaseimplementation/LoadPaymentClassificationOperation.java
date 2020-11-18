package com.akondi.business.packaging.payrolldatabaseimplementation;

import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrollimplementation.CommissionedClassification;
import com.akondi.business.packaging.payrollimplementation.HourlyClassification;
import com.akondi.business.packaging.payrollimplementation.SalariedClassification;
import com.akondi.business.packaging.payrollimplementation.SalesReceipt;
import com.akondi.business.packaging.payrollimplementation.TimeCard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoadPaymentClassificationOperation extends LoadOperation {
    private final String classificationCode;
    private PaymentClassification paymentClassification;

    private interface PaymentClassificationCreator {
        void execute(ResultSet result);
    }

    private PaymentClassificationCreator classificationCreator;
    private ResultSet resultSet;

    public LoadPaymentClassificationOperation(Employee employee, String classificationCode, Connection connection) {
        super(employee.getEmployerId(), connection);
        this.classificationCode = classificationCode;
    }

    @Override
    public void execute() {
        try {
            prepare();
            resultSet = loadData();
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private void prepare() {
        switch (classificationCode) {
            case "hourly":
                setTableName("HourlyEmployee");
                classificationCreator = this::createHourlyClassification;
                createPaymentClassification(resultSet);
                break;
            case "commissioned":
                setTableName("CommissionedEmployee");
                classificationCreator = this::createCommissionedClassification;
                createPaymentClassification(resultSet);
                break;
            case "salaried":
                setTableName("SalariedEmployee");
                classificationCreator = this::createSalariedClassification;
                createPaymentClassification(resultSet);
                break;
            case "unknown":
                break;
        }
    }

    private void createHourlyClassification(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                double hourlyRate = resultSet.getDouble("hourlyRate");
                setTableName("TimeCard");
                ResultSet resultSet1 = loadData();
                Map<Date, TimeCard> timeCardMap = extractTimeCards(resultSet1);
                HourlyClassification hourlyClassification = new HourlyClassification(hourlyRate);
                hourlyClassification.setTimeCardList(timeCardMap);
                paymentClassification = hourlyClassification;
            }
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private Map<Date, TimeCard> extractTimeCards(ResultSet resultSet) {
        try {
            Map<Date, TimeCard> timeCardMap = new HashMap<>();
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                double hours = resultSet.getDouble("hours");
                TimeCard timeCard = new TimeCard(date, hours);
                timeCardMap.put(date, timeCard);
            }
            return timeCardMap;
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private void createCommissionedClassification(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                double hourlyRate = resultSet.getDouble("hourlyRate");
                double commissionRate = resultSet.getDouble("commissionRate");
                setTableName("SalesReceipt");
                ResultSet resultSet1 = loadData();
                Map<Date, SalesReceipt> salesReceiptMap = extractSalesReceipts(resultSet1);
                CommissionedClassification commissionedClassification =
                        new CommissionedClassification(hourlyRate, commissionRate);
                commissionedClassification.setSalesReceiptMap(salesReceiptMap);
                paymentClassification = commissionedClassification;
            }
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private Map<Date, SalesReceipt> extractSalesReceipts(ResultSet resultSet) {
        try {
            Map<Date, SalesReceipt> salesReceiptMap = new HashMap<>();
            while (resultSet.next()) {
                Date date = resultSet.getDate("date");
                double amount = resultSet.getDouble("amount");
                SalesReceipt salesReceipt = new SalesReceipt(date, amount);
                salesReceiptMap.put(date, salesReceipt);
            }
            return salesReceiptMap;
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private void createSalariedClassification(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                double salary = resultSet.getDouble("salary");
                paymentClassification = new SalariedClassification(salary);
            }
        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    private void createPaymentClassification(ResultSet resultSet) {
        classificationCreator.execute(resultSet);
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }
}
