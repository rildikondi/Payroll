package com.akondi.java_ui;

import com.akondi.business.packaging.mvp.presenter.AddEmployeePresenter;
import com.akondi.business.packaging.mvp.presenter.TransactionContainer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class AddEmployeeWindowTest {
    private AddEmployeeWindow window;
    private AddEmployeePresenter presenter;
    private TransactionContainer transactionContainer;

    @Before
    public void setUp() {
        window = new AddEmployeeWindow();
        transactionContainer = new TransactionContainer(new ArrayList<>());
        presenter = new AddEmployeePresenter(window, transactionContainer, null);
        window.setPresenter(presenter);
        window.setVisible(true);
    }

    @Test
    public void testStartingState() {
        Assert.assertSame(presenter, window.getPresenter());
        Assert.assertFalse(window.submitButton.isEnabled());
        Assert.assertFalse(window.hourlyRateTextBox.isEnabled());
        Assert.assertFalse(window.salaryTextBox.isEnabled());
        Assert.assertFalse(window.commissionSalaryTextBox.isEnabled());
        Assert.assertFalse(window.commissionRateTextBox.isEnabled());
    }

    @Test
    public void testPresenterValuesAreSet() {
        window.empIdTextBox.setText("123");
        Assert.assertEquals(123, presenter.getEmployerId());
        window.nameTextBox.setText("John");
        Assert.assertEquals("John", presenter.getName());
        window.addressTextBox.setText("321 Somewhere");
        Assert.assertEquals("321 Somewhere", presenter.getAddress());
        window.hourlyRateTextBox.setText("123.45");
        Assert.assertEquals(123.45, presenter.getHourlyRate(), 0.01);
        window.salaryTextBox.setText("1234");
        Assert.assertEquals(1234, presenter.getSalary(), 0.01);
        window.commissionSalaryTextBox.setText("123");
        Assert.assertEquals(123, presenter.getCommissionSalary(), 0.01);
        window.commissionRateTextBox.setText("12.3");
        Assert.assertEquals(12.3, presenter.getCommission(), 0.01);
        window.hourlyRadioButton.doClick();
        Assert.assertTrue(presenter.getIsHourly());
        window.salaryRadioButton.doClick();
        Assert.assertTrue(presenter.getIsSalary());
        Assert.assertFalse(presenter.getIsHourly());
        window.commissionRadioButton.doClick();
        Assert.assertTrue(presenter.getIsCommission());
        Assert.assertFalse(presenter.getIsSalary());
    }

    @Test
    public void testEnablingHourlyFields() {
        window.hourlyRadioButton.setSelected(true);
        Assert.assertTrue(window.hourlyRateTextBox.isEnabled());
        window.paymentClassificationButtonGroup.clearSelection();
        Assert.assertFalse(window.hourlyRateTextBox.isEnabled());
    }

    @Test
    public void testEnablingSalaryFields() {
        window.salaryRadioButton.setSelected(true);
        Assert.assertTrue(window.salaryTextBox.isEnabled());
        window.paymentClassificationButtonGroup.clearSelection();
        Assert.assertFalse(window.salaryTextBox.isEnabled());
    }

    @Test
    public void testEnablingCommissionFields() {
        window.commissionRadioButton.setSelected(true);
        Assert.assertTrue(window.commissionRateTextBox.isEnabled());
        Assert.assertTrue(window.commissionSalaryTextBox.isEnabled());
        window.paymentClassificationButtonGroup.clearSelection();
        Assert.assertFalse(window.commissionRateTextBox.isEnabled());
        Assert.assertFalse(window.commissionSalaryTextBox.isEnabled());
    }

    @Test
    public void testEnablingAddEmployeeButton() {
        Assert.assertFalse(window.submitButton.isEnabled());
        window.setSubmitEnabled(true);
        Assert.assertTrue(window.submitButton.isEnabled());
        window.setSubmitEnabled(false);
        Assert.assertFalse(window.submitButton.isEnabled());
    }

    @Test
    public void testAddEmployee() {
        window.empIdTextBox.setText("123");
        window.nameTextBox.setText("John");
        window.addressTextBox.setText("321 Somewhere");
        window.hourlyRadioButton.setSelected(true);
        window.hourlyRateTextBox.setText("123.45");
        window.submitButton.doClick();
        Assert.assertFalse(window.isVisible());
        Assert.assertEquals(1, transactionContainer.getTransactions().size());
    }
}
