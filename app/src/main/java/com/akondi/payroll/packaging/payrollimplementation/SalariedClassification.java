package com.akondi.payroll.packaging.payrollimplementation;

import com.akondi.payroll.packaging.payrolldomain.Paycheck;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;

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
