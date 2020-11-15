package com.akondi.payroll.packaging.payrollimplementation;

public class PaycheckAddressData {
    private int employerId;
    private String address;

    public PaycheckAddressData(int employerId, String address) {
        this.employerId = employerId;
        this.address = address;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
