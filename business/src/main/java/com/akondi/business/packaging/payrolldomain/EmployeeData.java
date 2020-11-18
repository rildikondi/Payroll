package com.akondi.business.packaging.payrolldomain;

public class EmployeeData {
    private String name;
    private int employerId;
    private String address;
    private String paymentClassification;
    private String paymentSchedule;
    private String paymentMethod;
    private String affiliation;

    public EmployeeData(int employerId, String name, String address, String paymentClassification, String paymentSchedule, String paymentMethod, String affiliation) {
        this.employerId = employerId;
        this.name = name;
        this.address = address;
        this.paymentClassification = paymentClassification;
        this.paymentSchedule = paymentSchedule;
        this.paymentMethod = paymentMethod;
        this.affiliation = affiliation;
    }

    public String getName() {
        return name;
    }

    public int getEmployerId() {
        return employerId;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentClassification() {
        return paymentClassification;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getAffiliation() {
        return affiliation;
    }
}
