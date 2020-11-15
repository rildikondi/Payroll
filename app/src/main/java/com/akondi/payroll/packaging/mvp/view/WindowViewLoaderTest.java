package com.akondi.payroll.packaging.mvp.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packaging.mvp.presenter.PayrollPresenter;
import packaging.mvp.presenter.TransactionContainer;
import packaging.payrolldatabase.PayrollDatabase;
import packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;

import javax.swing.*;

public class WindowViewLoaderTest {
    private PayrollDatabase database;
    private WindowViewLoader viewLoader;

    @Before
    public void setUp() {
        database = new InMemoryPayrollDatabase();
        viewLoader = new WindowViewLoader(database);
    }

    @Test
    public void testLoadPayrollView() {
        viewLoader.loadPayrollView();
        JFrame form = viewLoader.getLastLoadedView();
        Assert.assertTrue(form instanceof PayrollWindow);
        Assert.assertTrue(form.isVisible());
        PayrollWindow payrollWindow = (PayrollWindow) form;
        PayrollPresenter presenter = payrollWindow.getPresenter();
        Assert.assertNotNull(presenter);
        Assert.assertSame(form, presenter.getView());
    }

    @Test
    public void testLoadAddEmployeeView() {
        viewLoader.loadAddEmployeeView(new TransactionContainer(null));
        JFrame form = viewLoader.getLastLoadedView();
        Assert.assertTrue(form instanceof AddEmployeeWindow);
        Assert.assertTrue(form.isVisible());
        AddEmployeeWindow addEmployeeWindow = (AddEmployeeWindow) form;
        Assert.assertNotNull(addEmployeeWindow.getPresenter());
    }
}