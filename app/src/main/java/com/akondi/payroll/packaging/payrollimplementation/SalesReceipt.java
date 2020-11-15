package com.akondi.payroll.packaging.payrollimplementation;

import java.util.Date;

public class SalesReceipt {
    private final Date date;
    private final double amount;

    public SalesReceipt(Date date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
