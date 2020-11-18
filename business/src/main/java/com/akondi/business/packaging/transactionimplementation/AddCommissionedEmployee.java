package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;

public class AddCommissionedEmployee extends AddEmployeeTransaction {
    private double salary;
    private double commissionRate;

    public AddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate, PayrollDatabase payrollDatabase) {
        super(empId, name, address, payrollDatabase);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    @Override
    protected PaymentClassification makeClassification() {
        return  PayrollFactory.payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return PayrollFactory.payrollFactory.makeBiweeklySchedule();
    }
}
