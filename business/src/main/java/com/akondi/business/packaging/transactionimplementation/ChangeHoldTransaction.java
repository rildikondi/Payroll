package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeMethodTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class ChangeHoldTransaction extends ChangeMethodTransaction {
    public ChangeHoldTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected PaymentMethod getMethod() {
        return PayrollFactory.payrollFactory.makeHoldMethod();
    }
}
