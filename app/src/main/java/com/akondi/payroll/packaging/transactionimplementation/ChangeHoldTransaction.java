package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeMethodTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentMethod;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeHoldTransaction extends ChangeMethodTransaction {
    public ChangeHoldTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected PaymentMethod getMethod() {
        return payrollFactory.makeHoldMethod();
    }
}
