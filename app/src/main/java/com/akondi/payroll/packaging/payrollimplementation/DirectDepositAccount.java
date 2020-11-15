package com.akondi.payroll.packaging.payrollimplementation;

public class DirectDepositAccount {
    private int employerId;
    private  String bank;
    private  String account;

    public DirectDepositAccount(int employerId, String bank, String account) {
        this.employerId = employerId;
        this.bank = bank;
        this.account = account;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
