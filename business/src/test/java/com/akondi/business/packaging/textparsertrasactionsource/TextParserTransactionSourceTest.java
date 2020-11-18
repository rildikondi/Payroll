package com.akondi.business.packaging.textparsertrasactionsource;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import com.akondi.payroll.packaging.transactionapplication.Transaction;
import com.akondi.payroll.packaging.transactionfactory.TransactionFactory;

import org.junit.Assert;
import org.junit.Test;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

public class TextParserTransactionSourceTest {

    private final Transaction addHourlyTransaction = new TestTransaction(new InMemoryPayrollDatabase());
    private final Transaction addCommissionedTransaction = new TestTransaction(new InMemoryPayrollDatabase());

    private TextParserTransactionSource source;

    @Test
    public void addCommissionedEmployee() throws Exception {
        given("AddEmp 2 \"Bill\" \"Work\" C 2500.00 3.2");
        Assert.assertSame(source.getTransaction() , addCommissionedTransaction);
    }

    @Test
    public void addHourlyEmployee() throws Exception {
        given("AddEmp 3 \"Lance\" \"Home\" H 15.25\n");
        Assert.assertSame(source.getTransaction(), addHourlyTransaction);
    }

    @Test
    public void canReadMultipleTransactions() throws Exception {
        given("AddEmp 2 \"Bill\" \"Work\" C 2500.00 3.2\nAddEmp 3 \"Lance\" \"Home\" H 15.25\n");
        Assert.assertSame(source.getTransaction() ,addCommissionedTransaction );
        Assert.assertSame(source.getTransaction(), addHourlyTransaction);
        Assert.assertSame(source.getTransaction(), null);
    }

    private void given(String transactions) throws Exception {
        InputStream input = new ByteArrayInputStream(transactions.getBytes("UTF-8"));
        source = new TextParserTransactionSource(new TestTransactionFactory(), input);
    }



    private class TestTransaction extends Transaction {

        public TestTransaction(PayrollDatabase payrollDatabase) {
            super(payrollDatabase);
        }

        @Override
        public void execute() {
        }

    }

    private class TestTransactionFactory extends TransactionFactory {

        @Override
        public Transaction makeAddCommissionedEmployee(int employeeId, String name, String address, double salary, double commissionRate) {
            Assert.assertEquals(employeeId, 2);
            Assert.assertEquals(name, "Bill");
            Assert.assertEquals(address, "Work");
            Assert.assertEquals(salary, 2500.00, 0.01);
            Assert.assertEquals(commissionRate, 3.2, 0.01);
            return addCommissionedTransaction;
        }

        @Override
        public Transaction makeAddHourlyEmployee(int employeeId, String name, String address, double hourlyRate) {
            Assert.assertEquals(employeeId, 3);
            Assert.assertEquals(name, "Lance");
            Assert.assertEquals(address, "Home");
            Assert.assertEquals(hourlyRate, 15.25, 0.01);
            return addHourlyTransaction;
        }

        @Override
        public Transaction makeAddSalariedEmployee(int employeeId, String name, String address, double salary) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeCommissionedTransaction(int employeeId, double salary, double commissionRate) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeHourlyTransaction(int employeeId, double hourlyRate) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeMemberTransaction(int employeeId, int memberId, double weeklyDues) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeNameTransaction(int employeeId, String name) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeSalariedTransaction(int employeeId, double salary) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeChangeMailTransaction(int empId, String address) {
            return null;
        }

        @Override
        public Transaction makeChangeDirectTransaction(int empId, String bank, String account) {
            return null;
        }

        @Override
        public Transaction makeChangeHoldTransaction(int empId) {
            return null;
        }

        @Override
        public Transaction makeChangeUnaffiliatedTransaction(int employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeDeleteEmployeeTransaction(int employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makePaydayTransaction(Date payDate) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeSalesReceiptTransaction(Date date, double amount, int employeeId) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeServiceChargeTransaction(int memberId, Date date, double charge) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Transaction makeTimeCardTransaction(Date date, double hours, int employeeId) {
            // TODO Auto-generated method stub
            return null;
        }
    }
}