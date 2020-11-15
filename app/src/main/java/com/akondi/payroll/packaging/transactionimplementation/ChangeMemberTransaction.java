package com.akondi.payroll.packaging.transactionimplementation;

import packaging.abstracttransactions.ChangeAffiliationTransaction;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldomain.Affiliation;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.UnionAffiliation;

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
