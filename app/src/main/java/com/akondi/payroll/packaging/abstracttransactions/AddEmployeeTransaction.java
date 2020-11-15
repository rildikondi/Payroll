package com.akondi.payroll.packaging.abstracttransactions;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentMethod;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;
import com.akondi.payroll.packaging.payrollimplementation.HoldMethod;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

public abstract class AddEmployeeTransaction extends Transaction {
    private final int employerId;
    private final String name;
    private final String address;

    public AddEmployeeTransaction(int employerId, String name, String address, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.employerId = employerId;
        this.name = name;
        this.address = address;
    }

    protected abstract PaymentClassification makeClassification();

    protected abstract PaymentSchedule makeSchedule();

    @Override
    public void execute() {
        PaymentClassification paymentClassification = makeClassification();
        PaymentSchedule paymentSchedule = makeSchedule();
        PaymentMethod paymentMethod = new HoldMethod();
        Employee e = new Employee(employerId, name, address);
        e.setClassification(paymentClassification);
        e.setSchedule(paymentSchedule);
        e.setMethod(paymentMethod);
        payrollDatabase.addEmployee(e);
    }
}
