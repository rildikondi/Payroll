package com.akondi.payroll.packaging.transactionimplementation;

import com.akondi.payroll.packaging.abstracttransactions.ChangeClassificationTransaction;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

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
        return payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return payrollFactory.makeBiweeklySchedule();
    }
}
