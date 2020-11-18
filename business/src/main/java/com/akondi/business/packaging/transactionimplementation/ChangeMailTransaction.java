package com.akondi.business.packaging.transactionimplementation;


import com.akondi.business.packaging.abstracttransactions.ChangeMethodTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class ChangeMailTransaction extends ChangeMethodTransaction {
    private final String address;

    public ChangeMailTransaction(int empId, String address, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.address = address;
    }

    @Override
    protected PaymentMethod getMethod() {
        return PayrollFactory.payrollFactory.makeMailMethod(address);
    }
}
