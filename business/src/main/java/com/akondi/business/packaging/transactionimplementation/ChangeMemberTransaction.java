package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeAffiliationTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Affiliation;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrollimplementation.UnionAffiliation;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

    private final int memberId;
    private final double dues;

    public ChangeMemberTransaction(int empId, int memberId, double dues, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
        this.memberId = memberId;
        this.dues = dues;
    }

    @Override
    protected void recordMembership(Employee e) {
        payrollDatabase.addUnionMember(memberId, e);
    }

    @Override
    protected Affiliation getAffiliation() {
        return new UnionAffiliation(memberId, dues);
    }
}
