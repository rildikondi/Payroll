package com.akondi.business.packaging.abstracttransactions;


import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.transactionapplication.Transaction;

public abstract class ChangeEmployeeTransaction extends Transaction {
    private final int empId;

    public ChangeEmployeeTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e =  payrollDatabase.getEmployee(empId);
        if (e != null)
            change(e);
        else
            throw new UnsupportedOperationException(
                    "No such employee.");
    }

    protected abstract void change(Employee e);
}
