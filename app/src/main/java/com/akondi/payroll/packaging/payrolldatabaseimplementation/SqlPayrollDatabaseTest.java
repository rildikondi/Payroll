package com.akondi.payroll.packaging.payrolldatabaseimplementation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packaging.payrolldomain.*;
import packaging.payrollimplementation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlPayrollDatabaseTest {
    protected Connection connection;
    protected ResultSet resultSet;
    private SqlPayrollDatabase database;
    private Employee employee;

    @Before
    public void setUp() {
        database = new SqlPayrollDatabase();
        openConnection();
        clearTable("Employee");
        clearTable("DirectDepositAccount");
        clearTable("PaycheckAddress");
        employee = new Employee(123, "George", "123 Baker St.");
        employee.setSchedule(new MonthlySchedule());
        employee.setMethod(new DirectDepositMethod("Bank 1", "123890"));
        employee.setClassification(new SalariedClassification(1000.00));
    }

    private void clearTable(String tableName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName);
            preparedStatement.execute();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @After
    public void tearDown() {
        close();
    }

    @Test
    public void testAddEmployee() {
        try {
            database.addEmployee(employee);
            EmployeeData[] employees = loadEmployeeTable();
            Assert.assertEquals(1, employees.length);
            Assert.assertEquals(123, employees[0].getEmployerId());
            Assert.assertEquals("George", employees[0].getName());
            Assert.assertEquals("123 Baker St.", employees[0].getAddress());
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private EmployeeData[] loadEmployeeTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee");
            ResultSet resultSet = preparedStatement.executeQuery();
            EmployeeData[] employees = extractEmployees(resultSet);
            resultSet.close();
            preparedStatement.close();
            return employees;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private EmployeeData[] extractEmployees(ResultSet resultSet) {
        try {
            List<EmployeeData> employees = new ArrayList<>();
            while (resultSet.next()) {
                int employerId = resultSet.getInt("employerId");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String paymentClassification = resultSet.getString("paymentClassification");
                String paymentSchedule = resultSet.getString("paymentSchedule");
                String paymentMethod = resultSet.getString("paymentMethod");
                String affiliation = resultSet.getString("affiliation");
                EmployeeData employeeData = new EmployeeData(
                        employerId, name, address, paymentClassification, paymentSchedule, paymentMethod, affiliation);
                employees.add(employeeData);
            }
            EmployeeData[] employeesArray = new EmployeeData[employees.size()];
            employeesArray = employees.toArray(employeesArray);
            return employeesArray;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testScheduleGetsSaved() {
        checkSavedScheduleCode(new MonthlySchedule(), "monthly");
        clearTable("Employee");
        checkSavedScheduleCode(new WeeklySchedule(), "weekly");
        clearTable("Employee");
        checkSavedScheduleCode(new BiweeklySchedule(), "biweekly");
    }

    private void checkSavedScheduleCode(PaymentSchedule paymentSchedule, String expectedCode) {
        try {
            employee.setSchedule(paymentSchedule);
            database.addEmployee(employee);
            EmployeeData[] employees = loadEmployeeTable();
            Assert.assertEquals(1, employees.length);
            Assert.assertEquals(expectedCode, employees[0].getPaymentSchedule());
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testPaymentMethodGetsSaved() {
        checkSavedPaymentMethodCode(new HoldMethod(), "hold");
        clearTable("Employee");
        checkSavedPaymentMethodCode(
                new DirectDepositMethod("Bank -1", "0987654321"), "direct deposit");
        clearTable("Employee");
        checkSavedPaymentMethodCode(new MailMethod("111 Maple Ct."), "mail");
    }

    private void checkSavedPaymentMethodCode(PaymentMethod method, String expectedCode) {
        try {
            employee.setMethod(method);
            database.addEmployee(employee);
            EmployeeData[] employees = loadEmployeeTable();
            Assert.assertEquals(expectedCode, employees[0].getPaymentMethod());
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testDirectDepositMethodGetsSaved() {
        try {
            checkSavedPaymentMethodCode(new DirectDepositMethod("Bank -1", "0987654321"), "direct deposit");
            DirectDepositAccount[] directDepositAccounts = loadDirectDepositAccount();
            Assert.assertEquals(123, directDepositAccounts[0].getEmployerId());
            Assert.assertEquals("Bank -1", directDepositAccounts[0].getBank());
            Assert.assertEquals("0987654321", directDepositAccounts[0].getAccount());

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testMailMethodGetsSaved() {
        checkSavedPaymentMethodCode(new MailMethod("111 Maple Ct."), "mail");
        PaycheckAddressData[] paycheckAddresses = loadPaycheckAddressTable();
        Assert.assertEquals(1, paycheckAddresses.length);
        Assert.assertEquals("111 Maple Ct.", paycheckAddresses[0].getAddress());
        Assert.assertEquals(123, paycheckAddresses[0].getEmployerId());
    }

    private PaycheckAddressData[] loadPaycheckAddressTable() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PaycheckAddress");
            ResultSet resultSet = preparedStatement.executeQuery();
            PaycheckAddressData[] paycheckAddressData = extractPaycheckAddressData(resultSet);
            resultSet.close();
            preparedStatement.close();
            return paycheckAddressData;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private PaycheckAddressData[] extractPaycheckAddressData(ResultSet resultSet) {
        try {
            List<PaycheckAddressData> paycheckAddressDataList = new ArrayList<>();
            while (resultSet.next()) {
                int employerId = resultSet.getInt("employerId");
                String address = resultSet.getString("address");

                PaycheckAddressData paycheckAddressData = new PaycheckAddressData(employerId, address);
                paycheckAddressDataList.add(paycheckAddressData);
            }
            PaycheckAddressData[] paycheckAddressDataArray = new PaycheckAddressData[paycheckAddressDataList.size()];
            paycheckAddressDataArray = paycheckAddressDataList.toArray(paycheckAddressDataArray);
            return paycheckAddressDataArray;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }


    private DirectDepositAccount[] loadDirectDepositAccount() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM DirectDepositAccount");
            ResultSet resultSet = preparedStatement.executeQuery();
            DirectDepositAccount[] directDepositAccounts = extractDirectDepositAccount(resultSet);
            resultSet.close();
            preparedStatement.close();
            return directDepositAccounts;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private DirectDepositAccount[] extractDirectDepositAccount(ResultSet resultSet) {
        try {
            List<DirectDepositAccount> directDepositAccounts = new ArrayList<>();
            while (resultSet.next()) {
                int employerId = resultSet.getInt("employerId");
                String bank = resultSet.getString("bank");
                String account = resultSet.getString("account");
                DirectDepositAccount directDepositAccount = new DirectDepositAccount(employerId, bank, account);
                directDepositAccounts.add(directDepositAccount);
            }
            DirectDepositAccount[] accounts = new DirectDepositAccount[directDepositAccounts.size()];
            accounts = directDepositAccounts.toArray(accounts);
            return accounts;
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Test
    public void testSaveIsTransactional() {
        // Null values won't go in the database.
        DirectDepositMethod method = new DirectDepositMethod(null, null);
        employee.setMethod(method);
        try {
            database.addEmployee(employee);
            Assert.fail("An exception needs to occur for this test to work.");
        } catch (Error error) {
        }
        EmployeeData[] employeeData = loadEmployeeTable();
        Assert.assertEquals(0, employeeData.length);
    }

    @Test
    public void testSaveMailMethodThenHoldMethod() {
        employee.setMethod(new MailMethod("123 Baker St."));
        database.addEmployee(employee);
        Employee employee2 = new Employee(321, "Ed", "456 Elm St.");
        employee2.setMethod(new HoldMethod());
        database.addEmployee(employee2);
        PaycheckAddressData[] paycheckAddressData = loadPaycheckAddressTable();
        Assert.assertEquals(1, paycheckAddressData.length);
    }

    @Test
    public void testLoadEmployee()
    {
        employee.setSchedule(new BiweeklySchedule());
        employee.setMethod(new DirectDepositMethod("1st Bank", "0123456"));
        employee.setClassification(new SalariedClassification(5432.10));
        database.addEmployee(employee);
        Employee loadedEmployee = database.getEmployee(123);
        Assert.assertEquals(123, loadedEmployee.getEmployerId());
        Assert.assertEquals(employee.getName(), loadedEmployee.getName());
        Assert.assertEquals(employee.getAddress(), loadedEmployee.getAddress());
        PaymentSchedule schedule = loadedEmployee.getPaymentSchedule();
        Assert.assertTrue(schedule instanceof BiweeklySchedule);
        PaymentMethod method = loadedEmployee.getPaymentMethod();
        Assert.assertTrue(method instanceof DirectDepositMethod);
        DirectDepositMethod ddMethod = (DirectDepositMethod) method ;
        Assert.assertEquals("1st Bank", ddMethod.getBank());
        Assert.assertEquals("0123456", ddMethod.getAccount());
        PaymentClassification classification = loadedEmployee.getPaymentClassification();
        Assert.assertTrue(classification instanceof SalariedClassification);
        SalariedClassification salariedClassification = (SalariedClassification) classification;
        Assert.assertEquals(5432.10, salariedClassification.getSalary(), 0.01);
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
            if (resultSet != null)
                resultSet.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }
}
