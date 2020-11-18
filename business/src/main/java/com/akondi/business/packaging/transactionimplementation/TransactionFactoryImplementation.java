package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.transactionfactory.TransactionFactory;
import com.akondi.business.packaging.transactionapplication.Transaction;

import java.util.Date;

public class TransactionFactoryImplementation extends TransactionFactory {

    private PayrollDatabase payrollDatabase;

    public TransactionFactoryImplementation(PayrollDatabase payrollDatabase) {

        this.payrollDatabase = payrollDatabase;
    }

    @Override
    public Transaction makeAddSalariedEmployee(int id, String name, String address, double salary) {
        return new AddSalariedEmployee(id, name, address, salary, payrollDatabase);
    }

    @Override
    public Transaction makeAddHourlyEmployee(int id, String name, String address, double hourlyRate) {
        return new AddHourlyEmployee(id, name, address, hourlyRate, payrollDatabase);
    }

    @Override
    public Transaction makeAddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate) {
        return new AddCommissionedEmployee(empId, name, address, salary, commissionRate, payrollDatabase);
    }

    @Override
    public Transaction makeDeleteEmployeeTransaction(int empId) {
        return new DeleteEmployeeTransaction(empId, payrollDatabase);
    }

    @Override
    public Transaction makePaydayTransaction(Date payDate) {
        return new PaydayTransaction(payDate, payrollDatabase);
    }

    @Override
    public Transaction makeTimeCardTransaction(Date date, double hours, int empId) {
        return new TimeCardTransaction(date, hours, empId, payrollDatabase);
    }

    @Override
    public Transaction makeSalesReceiptTransaction(Date date, double amount, int empId) {
        return new SalesReceiptTransaction(date, amount, empId, payrollDatabase);
    }

    @Override
    public Transaction makeChangeNameTransaction(int empId, String newName) {
        return new ChangeNameTransaction(empId, newName, payrollDatabase);
    }

    @Override
    public Transaction makeChangeHourlyTransaction(int id, double hourlyRate) {
        return new ChangeHourlyTransaction(id, hourlyRate, payrollDatabase);
    }

    @Override
    public Transaction makeChangeCommissionedTransaction(int empId, double salary, double commissionRate) {
        return new ChangeCommissionedTransaction(empId, salary, commissionRate, payrollDatabase);
    }

    @Override
    public Transaction makeChangeSalariedTransaction(int empId, double salary) {
        return new ChangeSalariedTransaction( empId,  salary, payrollDatabase);
    }

    @Override
    public Transaction makeChangeMailTransaction(int empId, String address) {
        return new ChangeMailTransaction( empId,  address, payrollDatabase);
    }

    @Override
    public Transaction makeChangeDirectTransaction(int empId, String bank , String account) {
        return new ChangeDirectTransaction(empId, bank , account, payrollDatabase);
    }

    @Override
    public Transaction makeChangeHoldTransaction(int empId) {
        return new ChangeHoldTransaction(empId, payrollDatabase);
    }

    @Override
    public Transaction makeChangeMemberTransaction(int empId, int memberId, double dues) {
        return new ChangeMemberTransaction( empId,  memberId,  dues, payrollDatabase);
    }

    @Override
    public Transaction makeChangeUnaffiliatedTransaction(int empId) {
        return new ChangeUnaffiliatedTransaction(empId, payrollDatabase);
    }

    @Override
    public Transaction makeServiceChargeTransaction(int memberId, Date date, double charge) {
        return new ServiceChargeTransaction(memberId, date, charge, payrollDatabase);
    }
}
