package com.akondi.payroll.packaging.transactionimplementation;

import com.akondi.payroll.packaging.abstracttransactions.AddEmployeeTransaction;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.payrolldomain.PaymentSchedule;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;

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
        return  payrollFactory.makeCommissionedClassification(salary, commissionRate);
    }

    @Override
    protected PaymentSchedule makeSchedule() {
        return payrollFactory.makeBiweeklySchedule();
    }
}
