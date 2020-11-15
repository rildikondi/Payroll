package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeMethodTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.PaymentMethod;
import packaging.payrollfactory.PayrollFactory;

public class ChangeDirectTransaction extends ChangeMethodTransaction {
    private final String bank;
    private final String account;

    public ChangeDirectTransaction(int empId, String bank, String account, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.bank = bank;
        this.account = account;
    }

    @Override
    protected PaymentMethod getMethod() {
        return PayrollFactory.payrollFactory.makeDirectMethod(bank, account);
    }
}
