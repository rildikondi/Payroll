package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeEmployeeTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Employee;

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
