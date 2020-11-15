package com.akondi.payroll.packaging.payrolldomain;

import java.util.Date;

public class Employee {
    private String name;
    private int employerId;
    private String address;
    private PaymentClassification paymentClassification;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;
    private Affiliation affiliation;

    public Employee(int employerId, String name, String address) {
        this.employerId = employerId;
        this.name = name;
        this.address = address;
    }

    public int getEmployerId() {
        return employerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassification(PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public void setSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public void setMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public Affiliation getAffiliation() {
        return affiliation != null ? affiliation : Affiliation.NULL;
    }

    public boolean isPayDate(Date payDate) {
        return paymentSchedule.isPayDay(payDate);
    }

    public void payday(Paycheck paycheck) {
        double grossPay = getPaymentClassification().calculatePay(paycheck);
        double deductions = getAffiliation().calculateDeductions(paycheck) ;
        double netPay = grossPay - deductions;
        paycheck.setGrossPay(grossPay);
        paycheck.setDeductions(deductions);
        paycheck.setNetPay(netPay);
        paymentMethod.pay(paycheck);
    }

    public Date getPayPeriodStartDate(Date payDate) {
        return paymentSchedule.getPayPeriodStartDate(payDate);
    }
}
