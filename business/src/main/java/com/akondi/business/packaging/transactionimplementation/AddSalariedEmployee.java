package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class AddSalariedEmployee extends AddEmployeeTransaction {
    private final double salary;

    public AddSalariedEmployee(int id, String name, String address, double salary, PayrollDatabase payrollDatabase){
        super(id, name, address, payrollDatabase);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return PayrollFactory.payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return PayrollFactory.payrollFactory.makeMonthlySchedule();
    }
}
