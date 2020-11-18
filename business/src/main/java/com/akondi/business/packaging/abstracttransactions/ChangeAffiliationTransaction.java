package com.akondi.business.packaging.abstracttransactions;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Affiliation;
import com.akondi.business.packaging.payrolldomain.Employee;

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
