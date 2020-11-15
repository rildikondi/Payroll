package com.akondi.payroll.packaging.transactionimplementation;

import com.akondi.payroll.packaging.abstracttransactions.ChangeClassificationTransaction;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private final double hourlyRate;

    public ChangeHourlyTransaction(int id, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeWeeklySchedule();
    }
}
