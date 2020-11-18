package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeClassificationTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class ChangeCommissionedTransaction extends ChangeClassificationTransaction {
    private final double salary;
    private final double commissionRate;

    public ChangeCommissionedTransaction(int empId, double salary, double commissionRate, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification getClassification() {
        return PayrollFactory.payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return PayrollFactory.payrollFactory.makeBiweeklySchedule();
    }
}
