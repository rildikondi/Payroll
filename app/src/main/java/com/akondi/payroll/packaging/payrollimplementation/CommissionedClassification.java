package com.akondi.payroll.packaging.payrollimplementation;

import com.akondi.payroll.packaging.payrolldomain.Paycheck;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommissionedClassification implements PaymentClassification {
    private double salary;
    private double commissionRate;

    private Map<Date, SalesReceipt> salesReceiptMap = new HashMap<>();

    public CommissionedClassification(double salary, double commissionRate) {
        super();
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public void setSalesReceiptMap(Map<Date, SalesReceipt> salesReceiptMap) {
        this.salesReceiptMap = salesReceiptMap;
    }

    public double getSalary() {
        return salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceiptMap.put(salesReceipt.getDate(), salesReceipt);
    }

    public SalesReceipt getSalesReceipt(Date date) {
        return salesReceiptMap.get(date);
    }

    public Map<Date, SalesReceipt> getSalesReceiptMap() {
        return salesReceiptMap;
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        double totalSales = 0.00;
        for (SalesReceipt salesReceipt : salesReceiptMap.values()) {
            if (DateUtil.isInPayPeriod(salesReceipt.getDate(), paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate())) {
                totalSales += salesReceipt.getAmount();
            }
        }
        return salary / 2 + totalSales * commissionRate;
    }
}
