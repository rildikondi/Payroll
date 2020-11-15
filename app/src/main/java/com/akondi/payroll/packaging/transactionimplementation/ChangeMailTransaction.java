package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeMethodTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentMethod;

import static packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeMailTransaction extends ChangeMethodTransaction {
    private final String address;

    public ChangeMailTransaction(int empId, String address, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.address = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return payrollFactory.makeMailMethod(address);
    }
}
