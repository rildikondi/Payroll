package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.ChangeAffiliationTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Affiliation;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrollimplementation.UnionAffiliation;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int empId, PayrollDatabase payrollDatabase) {
        super(empId, payrollDatabase);
    }

    @Override
    protected void recordMembership(Employee e) {
        Affiliation affiliation = e.getAffiliation();
        if (affiliation instanceof UnionAffiliation) {
            UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
            int memberId = unionAffiliation.getMemberId();
            payrollDatabase.removeUnionMember(memberId);
        }
    }

    @Override
    protected Affiliation getAffiliation() {
        return Affiliation.NULL;
    }
}
