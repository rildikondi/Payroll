package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeClassificationTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentClassification;
import packaging.payrolldomain.PaymentSchedule;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
    private final double salary;

    public ChangeSalariedTransaction(int empId, double salary, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeMonthlySchedule();
    }
}
