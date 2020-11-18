package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.transactionapplication.Transaction;

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
