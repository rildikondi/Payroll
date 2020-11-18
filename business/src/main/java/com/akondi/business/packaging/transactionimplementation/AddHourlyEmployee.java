package com.akondi.business.packaging.transactionimplementation;


import com.akondi.business.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class AddHourlyEmployee extends AddEmployeeTransaction {
    private final double hourlyRate;

    public AddHourlyEmployee(int id, String name, String address, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, name, address, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return PayrollFactory.payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return PayrollFactory.payrollFactory.makeWeeklySchedule();
    }
}
