package com.akondi.business.packaging.abstracttransactions;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;

public abstract class ChangeMethodTransaction extends ChangeEmployeeTransaction {

    public ChangeMethodTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected void change(Employee e) {
        e.setMethod(getMethod());
    }

    protected abstract PaymentMethod getMethod();
}
