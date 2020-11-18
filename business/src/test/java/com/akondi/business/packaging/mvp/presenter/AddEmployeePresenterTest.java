package com.akondi.business.packaging.mvp.presenter;

import com.akondi.payroll.packaging.mvp.view.MockAddEmployeeView;
import com.akondi.payroll.packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import com.akondi.payroll.packaging.transactionapplication.Transaction;
import com.akondi.payroll.packaging.transactionimplementation.AddCommissionedEmployee;
import com.akondi.payroll.packaging.transactionimplementation.AddHourlyEmployee;
import com.akondi.payroll.packaging.transactionimplementation.AddSalariedEmployee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AddEmployeePresenterTest {
    private AddEmployeePresenter presenter;
    private TransactionContainer container;
    private InMemoryPayrollDatabase database;
    private MockAddEmployeeView view;

    @Before
    public void setUp() {
        view = new MockAddEmployeeView();
        container = new TransactionContainer(null);
        database = new InMemoryPayrollDatabase();
        presenter = new AddEmployeePresenter(
                view, container, database);
    }

    @Test
    public void testCreation() {
        Assert.assertSame(container, presenter.getTransactionContainer());
    }

    @Test
    public void testAllInfoIsCollected() {
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setEmployerId(1);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setName("Bill");
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setAddress("123 abc");
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setIsHourly(true);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setHourlyRate(1.23);
        Assert.assertTrue(presenter.allInformationIsCollected());
        presenter.setIsHourly(false);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setIsSalary(true);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setSalary(1234);
        Assert.assertTrue(presenter.allInformationIsCollected());
        presenter.setIsSalary(false);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setIsCommission(true);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setCommissionSalary(123);
        Assert.assertFalse(presenter.allInformationIsCollected());
        presenter.setCommission(12);
        Assert.assertTrue(presenter.allInformationIsCollected());
    }

    @Test
    public void testViewGetsUpdated() {
        presenter.setEmployerId(1);
        checkSubmitEnabled(false, 1);
        presenter.setName("Bill");
        checkSubmitEnabled(false, 2);
        presenter.setAddress("123 abc");
        checkSubmitEnabled(false, 3);
        presenter.setIsHourly(true);
        checkSubmitEnabled(false, 4);
        presenter.setHourlyRate(1.23);
        checkSubmitEnabled(true, 5);
    }

    private void checkSubmitEnabled(boolean expected, int count) {
        Assert.assertEquals(expected, view.submitEnabled);
        Assert.assertEquals(count, view.submitEnabledCount);
        view.submitEnabled = false;
    }

    @Test
    public void testCreatingTransaction() {
        presenter.setEmployerId(123);
        presenter.setName("Joe");
        presenter.setAddress("314 Elm");
        presenter.setIsHourly(true);
        presenter.setHourlyRate(10);
        Assert.assertTrue(presenter.createTransaction() instanceof AddHourlyEmployee);

        presenter.setIsHourly(false);
        presenter.setIsSalary(true);
        presenter.setSalary(3000);
        Assert.assertTrue(presenter.createTransaction() instanceof AddSalariedEmployee);

        presenter.setIsSalary(false);
        presenter.setIsCommission(true);
        presenter.setCommissionSalary(1000);
        presenter.setCommission(25);
        Assert.assertTrue(presenter.createTransaction() instanceof AddCommissionedEmployee);
    }

    @Test
    public void testAddEmployee() {
        container = new TransactionContainer(new ArrayList<Transaction>());
        presenter = new AddEmployeePresenter(view, container, database);
        presenter.setEmployerId(123);
        presenter.setName("Joe");
        presenter.setAddress("314 Elm");
        presenter.setIsHourly(true);
        presenter.setHourlyRate(25);
        presenter.addEmployee();
        Assert.assertEquals(1, container.getTransactions().size());
        Assert.assertTrue(container.getTransactions().get(0) instanceof AddHourlyEmployee);
    }
}