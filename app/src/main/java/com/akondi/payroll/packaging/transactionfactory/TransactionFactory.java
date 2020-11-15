package com.akondi.payroll.packaging.transactionfactory;

import com.akondi.payroll.packaging.transactionapplication.Transaction;

import java.util.Date;

public abstract class TransactionFactory {
    public static TransactionFactory transactionFactory;

    public abstract Transaction makeAddSalariedEmployee(int id, String name, String address, double salary);

    public abstract Transaction makeAddHourlyEmployee(int id, String name, String address, double hourlyRate);

    public abstract Transaction makeAddCommissionedEmployee(int empId, String name, String address, double salary, double commissionRate);

    public abstract Transaction makeDeleteEmployeeTransaction(int empId);

    public abstract Transaction makePaydayTransaction(Date payDate);

    public abstract Transaction makeTimeCardTransaction(Date date, double hours, int empId);

    public abstract Transaction makeSalesReceiptTransaction(Date date, double amount, int empId);

    public abstract Transaction makeChangeNameTransaction(int empId, String newName);

    public abstract Transaction makeChangeHourlyTransaction(int id, double hourlyRate);

    public abstract Transaction makeChangeCommissionedTransaction(int empId, double salary, double commissionRate);

    public abstract Transaction makeChangeSalariedTransaction(int empId, double salary);

    public abstract Transaction makeChangeMailTransaction(int empId, String address);

    public abstract Transaction makeChangeDirectTransaction(int empId, String bank, String account);

    public abstract Transaction makeChangeHoldTransaction(int empId);

    public abstract Transaction makeChangeMemberTransaction(int empId, int memberId, double dues);

    public abstract Transaction makeChangeUnaffiliatedTransaction(int empId);

    public abstract Transaction makeServiceChargeTransaction(int memberId, Date date, double charge);
}
