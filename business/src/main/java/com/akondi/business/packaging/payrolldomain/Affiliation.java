package com.akondi.business.packaging.payrolldomain;

import com.akondi.business.packaging.payrollfactory.PayrollFactory;
import com.akondi.business.packaging.payrollimplementation.ServiceCharge;

import java.util.Date;

public abstract class Affiliation {

    public abstract ServiceCharge getServiceCharge(Date date);

    public abstract void addServiceCharge(ServiceCharge serviceCharge);

    public static final Affiliation NULL = new NoAffiliation();

    public abstract double calculateDeductions(Paycheck paycheck);

    private static class NoAffiliation extends Affiliation {

        @Override
        public ServiceCharge getServiceCharge(Date date) {
            return PayrollFactory.payrollFactory.makeServiceCharge(date, 0.00);
        }

        @Override
        public void addServiceCharge(ServiceCharge serviceCharge) {

        }

        @Override
        public double calculateDeductions(Paycheck paycheck) {
            return 0.00;
        }
    }
}
