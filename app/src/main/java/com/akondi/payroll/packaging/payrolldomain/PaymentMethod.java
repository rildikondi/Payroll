package com.akondi.payroll.packaging.payrolldomain;

public interface PaymentMethod {
    void pay(Paycheck paycheck);
}
