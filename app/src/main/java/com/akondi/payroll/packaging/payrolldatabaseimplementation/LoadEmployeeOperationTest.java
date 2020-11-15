package com.akondi.payroll.packaging.payrolldatabaseimplementation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packaging.payrolldomain.Employee;
import packaging.payrollimplementation.BiweeklySchedule;
import packaging.payrollimplementation.HoldMethod;
import packaging.payrollimplementation.MonthlySchedule;
import packaging.payrollimplementation.WeeklySchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class LoadEmployeeOperationTest {
    private LoadEmployeeOperation loadOperation;
    private SaveEmployeeOperation saveEmployeeOperation;
    private Employee employee;
    private Connection connection;
    private Employee employeeRetrieved;


    @Before
    public void setUp() {
        openConnection();
        employee = new Employee(123, "Jean", "10 Rue de Roi");
        loadOperation = new LoadEmployeeOperation(123, connection);
    }

    @After
    public void tearDown() {
        clearTable("Employee");
        close();
    }

    private void clearTable(String tableName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testLoadingEmployeeDataCommand(){
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertEquals(123, employeeRetrieved.getEmployerId());
    }

    @Test
    public void testLoadEmployeeData() {
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertEquals("Jean", employeeRetrieved.getName());
        Assert.assertEquals("10 Rue de Roi", employeeRetrieved.getAddress());
    }

    @Test
    public void testLoadingSchedules(){
        employee.setSchedule(new WeeklySchedule());
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertNotNull(employeeRetrieved.getPaymentSchedule());
        Assert.assertTrue( employeeRetrieved.getPaymentSchedule() instanceof WeeklySchedule);
        clearTable("Employee");

        employee.setSchedule(new BiweeklySchedule());
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertNotNull(employeeRetrieved.getPaymentSchedule());
        Assert.assertTrue( employeeRetrieved.getPaymentSchedule() instanceof BiweeklySchedule);
        clearTable("Employee");

        employee.setSchedule(new MonthlySchedule());
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertNotNull(employeeRetrieved.getPaymentSchedule());
        Assert.assertTrue( employeeRetrieved.getPaymentSchedule() instanceof MonthlySchedule);
    }

    @Test
    public void testLoadingHoldMethod() {
        employee.setMethod(new HoldMethod());
        saveEmployeeOperation = new SaveEmployeeOperation(employee, connection);
        saveEmployeeOperation.execute();
        loadOperation.execute();
        employeeRetrieved = loadOperation.getEmployee();
        Assert.assertNotNull(employeeRetrieved.getPaymentMethod());
        Assert.assertTrue( employeeRetrieved.getPaymentMethod() instanceof HoldMethod);
    }


    private void openConnection() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Amarildo\\Desktop\\payroll.mdb", "", "");
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    protected void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }
}
