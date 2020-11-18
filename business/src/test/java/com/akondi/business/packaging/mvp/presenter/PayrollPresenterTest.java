package com.akondi.business.packaging.mvp.presenter;

import com.akondi.payroll.packaging.mvp.view.MockPayrollView;
import com.akondi.payroll.packaging.mvp.view.MockViewLoader;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;
import com.akondi.payroll.packaging.transactionapplication.Transaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PayrollPresenterTest {
    private MockPayrollView view;
    private PayrollPresenter presenter;
    private PayrollDatabase database;
    private MockViewLoader viewLoader;

    @Before
    public void setUp() {
        view = new MockPayrollView();
        database = new InMemoryPayrollDatabase();
        viewLoader = new MockViewLoader();
        presenter = new PayrollPresenter(database, viewLoader);
        presenter.setView(view);
    }

    @Test
    public void testCreation() {
        Assert.assertSame(view, presenter.getView());
        Assert.assertSame(database, presenter.getDatabase());
        Assert.assertNotNull(presenter.getTransactionContainer());
    }

    @Test
    public void testAddAction() {
        TransactionContainer container = presenter.getTransactionContainer();
        Transaction transaction = new MockTransaction(database);
        container.add(transaction);
        String expected = transaction.toString()
                + System.lineSeparator();
        Assert.assertEquals(expected, view.transactionsText);
    }

    @Test
    public void testAddEmployeeAction() {
        presenter.addEmployeeActionInvoked();
        Assert.assertTrue(viewLoader.addEmployeeViewWasLoaded);
    }

    @Test
    public void testRunTransactions() {
        MockTransaction transaction = new MockTransaction(database);
        presenter.getTransactionContainer().add(transaction);
        Employee employee = new Employee(123, "John", "123 Baker St.");
        database.addEmployee(employee);
        presenter.runTransactions();
        Assert.assertTrue(transaction.wasExecuted);
        Assert.assertEquals("", view.transactionsText);
        String expectedEmployeeTest = employee.toString()
                + System.lineSeparator();
        Assert.assertEquals(expectedEmployeeTest, view.employeesText);
    }
}