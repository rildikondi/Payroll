package com.akondi.java_ui;

import com.akondi.business.packaging.mvp.presenter.MockPayrollPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PayrollWindowTest {

    private PayrollWindow window;
    private MockPayrollPresenter presenter;


    @Before
    public void setUp() {
        window = new PayrollWindow();
        presenter = new MockPayrollPresenter(null, null);
        window.setPresenter(presenter);
        window.setVisible(true);
    }

    @After
    public void tearDown() {
        window.dispose();
    }

    @Test
    public void TransactionsText() {
        window.setTransactionsText("abc 123");
        Assert.assertEquals("abc 123", window.transactionsTextBox.getText());
    }

    @Test
    public void testEmployeesText() {
        window.setEmployeesText("some employee");
        Assert.assertEquals("some employee", window.employeesTextBox.getText());
    }

    @Test
    public void testAddEmployeeAction() {
        window.addEmployeeMenuItem.doClick();
        Assert.assertTrue(presenter.addEmployeeActionInvoked);
    }

    @Test
    public void testRunTransactions() {
        window.runButton.doClick();
        Assert.assertTrue(presenter.runTransactionCalled);
    }
}