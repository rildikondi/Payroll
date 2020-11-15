package com.akondi.payroll.packaging.payrolldomain;

import java.util.Date;

public class Paycheck {
    private Date payPeriodStartDate;
    private Date payPeriodEndDate;
    private double grossPay;
    private double deductions;
    private double netPay;
    private String disposition = "Hold";

    public Paycheck(Date payPeriodStartDate, Date payPeriodEndDate) {
        this.payPeriodStartDate = payPeriodStartDate;
        this.payPeriodEndDate = payPeriodEndDate;
    }

    public Date getPayPeriodStartDate() {
        return payPeriodStartDate;
    }

    public Date getPayPeriodEndDate() {
        return payPeriodEndDate;
    }

    public double getGrossPay() {
        return grossPay;
    }

    public String getField(String disposition) {
        return this.disposition;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setGrossPay(double grossPay) {
        this.grossPay = grossPay;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }
}
