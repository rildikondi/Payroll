package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeClassificationTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {
    private final double hourlyRate;

    public ChangeHourlyTransaction(int id, double hourlyRate, PayrollDatabase payrollDatabase) {
        super(id, payrollDatabase);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return PayrollFactory.payrollFactory.makeHourlyClassification(hourlyRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return PayrollFactory.payrollFactory.makeWeeklySchedule();
    }
}
