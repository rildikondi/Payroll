package com.akondi.payroll.packaging.abstracttransactions;


import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

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
