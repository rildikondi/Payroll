package com.akondi.payroll.packaging.transactionimplementation;

import com.akondi.payroll.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

public class AddSalariedEmployee extends AddEmployeeTransaction {
    private final double salary;

    public AddSalariedEmployee(int id, String name, String address, double salary, PayrollDatabase payrollDatabase){
        super(id, name, address, payrollDatabase);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeMonthlySchedule();
    }
}
