package com.akondi.payroll.packaging.transactionimplementation;


import com.akondi.payroll.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

public class AddHourlyEmployee extends AddEmployeeTransaction {
    private final double hourlyRate;

    public AddHourlyEmployee(int id, String name, String address, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, name, address, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeWeeklySchedule();
    }
}
