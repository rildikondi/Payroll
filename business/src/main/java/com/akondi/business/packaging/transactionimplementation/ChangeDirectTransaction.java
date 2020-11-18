package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeMethodTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

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
