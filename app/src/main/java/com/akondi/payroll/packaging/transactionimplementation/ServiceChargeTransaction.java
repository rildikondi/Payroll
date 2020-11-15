package com.akondi.payroll.packaging.transactionimplementation;


import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.payrollimplementation.UnionAffiliation;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

import java.util.Date;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

public class ServiceChargeTransaction extends Transaction {
    private final int memberId;
    private final Date date;
    private final double charge;

    public ServiceChargeTransaction(int memberId, Date date, double charge, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.memberId = memberId;
        this.date = date;
        this.charge = charge;
    }

    @Override
    public void execute() {
        Employee e =  payrollDatabase.getUnionMember(memberId);
        if (e != null) {
            if (e.getAffiliation() instanceof UnionAffiliation)
                e.getAffiliation().addServiceCharge(payrollFactory.makeServiceCharge(date, charge));
            else
                throw new UnsupportedOperationException(
                        "Tries to add service charge to union"
                                + "member without a union affiliation");
        } else
            throw new UnsupportedOperationException(
                    "No such union member.");
    }
}
