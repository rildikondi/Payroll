package com.akondi.payroll.packaging.payrollimplementation;

import packaging.payrolldomain.Paycheck;
import packaging.payrolldomain.PaymentMethod;

public class DirectDepositMethod implements PaymentMethod {
    private final String bank;
    private final String account;

    public DirectDepositMethod(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public void pay(Paycheck paycheck) {

    }
}
