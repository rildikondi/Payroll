package com.akondi.payroll.packaging.transactionimplementation;

import packaging.payrolldatabase.PayrollDatabase;
import packaging.transactionapplication.Transaction;

public class DeleteEmployeeTransaction extends Transaction {

    private final int empId;

    public DeleteEmployeeTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.empId = empId;
    }

    @Override
    public void execute() {
        payrollDatabase.deleteEmployee(empId);
    }
}
