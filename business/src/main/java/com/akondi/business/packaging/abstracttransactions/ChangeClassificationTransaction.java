package com.akondi.business.packaging.abstracttransactions;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;

public abstract class ChangeClassificationTransaction extends ChangeEmployeeTransaction {

    public ChangeClassificationTransaction(int id, PayrollDatabase payrollDatabase) {
        super(id, payrollDatabase);
    }

    @Override
    protected void change(Employee e) {
        e.setClassification(getClassification());
        e.setSchedule(getSchedule());
    }

    protected abstract PaymentClassification getClassification();


    protected abstract PaymentSchedule getSchedule();
}
