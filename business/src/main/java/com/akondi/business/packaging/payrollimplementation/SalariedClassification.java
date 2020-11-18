package com.akondi.business.packaging.payrollimplementation;

import com.akondi.business.packaging.payrolldomain.Paycheck;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;

public class SalariedClassification implements PaymentClassification {
    private double salary;

    public SalariedClassification(double salary) {
        super();
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        return salary;
    }
}
