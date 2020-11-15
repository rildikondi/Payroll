package com.akondi.payroll.packaging.abstracttransactions;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Affiliation;
import com.akondi.payroll.packaging.payrolldomain.Employee;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {
    
    public ChangeAffiliationTransaction(int empId, PayrollDatabase database) {
        super(empId, database);
    }

    @Override
    protected void change(Employee e) {
        recordMembership(e);
        e.setAffiliation(getAffiliation());
    }

    protected abstract void recordMembership(Employee e);

    protected abstract Affiliation getAffiliation();
}
