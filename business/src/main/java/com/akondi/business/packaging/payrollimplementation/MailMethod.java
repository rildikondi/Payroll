package com.akondi.business.packaging.payrollimplementation;

import com.akondi.business.packaging.payrolldomain.Paycheck;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;

public class MailMethod implements PaymentMethod {
    private String address;

    public MailMethod(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public void pay(Paycheck paycheck) {

    }
}
