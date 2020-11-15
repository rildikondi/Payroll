package com.akondi.payroll.packaging.mvp.presenter;

import com.akondi.payroll.packaging.mvp.view.AddEmployeeView;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.transactionapplication.Transaction;
import com.akondi.payroll.packaging.transactionimplementation.AddCommissionedEmployee;
import com.akondi.payroll.packaging.transactionimplementation.AddHourlyEmployee;
import com.akondi.payroll.packaging.transactionimplementation.AddSalariedEmployee;

public class AddEmployeePresenter {
    private TransactionContainer transactionContainer;
    private AddEmployeeView view;
    private PayrollDatabase database;
    private int employerId;
    private String name;
    private String address;
    private boolean isHourly;
    private double hourlyRate;
    private boolean isSalary;
    private double salary;
    private boolean isCommission;
    private double commissionSalary;
    private double commission;

    public AddEmployeePresenter(AddEmployeeView view,
                                TransactionContainer container,
                                PayrollDatabase database) {
        this.view = view;
        this.transactionContainer = container;
        this.database = database;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
        updateView();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateView();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        updateView();
    }

    public boolean getIsHourly() {
        return isHourly;
    }

    public void setIsHourly(boolean value) {
        this.isHourly = value;
        updateView();
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
        updateView();
    }

    public boolean getIsSalary() {
        return isSalary;
    }

    public void setIsSalary(boolean value) {
        isSalary = value;
        updateView();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double value) {
        salary = value;
        updateView();
    }

    public boolean getIsCommission() {
        return isCommission;
    }

    public void setIsCommission(boolean value) {
        isCommission = value;
        updateView();
    }

    public double getCommissionSalary() {
        return commissionSalary;
    }

    public void setCommissionSalary(double value) {
        commissionSalary = value;
        updateView();
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double value) {
        commission = value;
        updateView();
    }

    private void updateView() {
        if (allInformationIsCollected())
            view.setSubmitEnabled(true);
        else
            view.setSubmitEnabled(false);
    }

    public boolean allInformationIsCollected() {
        boolean result = true;
        result &= employerId > 0;
        result &= name != null && name.length() > 0;
        result &= address != null && address.length() > 0;
        result &= isHourly || isSalary || isCommission;
        if (isHourly)
            result &= hourlyRate > 0;
        else if (isSalary)
            result &= salary > 0;
        else if (isCommission) {
            result &= commission > 0;
            result &= commissionSalary > 0;
        }
        return result;
    }

    public TransactionContainer getTransactionContainer() {
        return transactionContainer;
    }

    public void addEmployee() {
        transactionContainer.add(createTransaction());
    }

    public Transaction createTransaction() {
        if (isHourly)
            return new AddHourlyEmployee(
                    employerId, name, address, hourlyRate, database);
        else if (isSalary)
            return new AddSalariedEmployee(
                    employerId, name, address, salary, database);
        else
            return new AddCommissionedEmployee(
                    employerId, name, address, commissionSalary,
                    commission, database);
    }
}
