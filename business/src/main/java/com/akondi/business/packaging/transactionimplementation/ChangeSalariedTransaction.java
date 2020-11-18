package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeClassificationTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class ChangeSalariedTransaction extends ChangeClassificationTransaction {
    private final double salary;

    public ChangeSalariedTransaction(int empId, double salary, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getClassification() {
        return PayrollFactory.payrollFactory.makeSalariedClassification(salary);
    }

    @Override
    protected PaymentSchedule getSchedule() {
        return PayrollFactory.payrollFactory.makeMonthlySchedule();
    }
}
