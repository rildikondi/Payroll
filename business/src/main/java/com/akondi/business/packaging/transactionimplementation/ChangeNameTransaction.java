package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeEmployeeTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;

public class ChangeNameTransaction extends ChangeEmployeeTransaction {
    private final String newName;

    public ChangeNameTransaction(int empId, String newName, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.newName = newName;
    }

    @Override
    protected void change(Employee e) {
        e.setName(newName);
    }
}
