package com.akondi.business.packaging.payrolldatabaseimplementation;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;

import java.sql.*;
import java.util.List;

public class SqlPayrollDatabase implements PayrollDatabase {
    private final Connection connection;
    private String methodCode;

    public SqlPayrollDatabase() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Amarildo\\Desktop\\payroll.mdb", "", "");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    @Override
    public void addEmployee( Employee employee) {
       SaveEmployeeOperation operation = new SaveEmployeeOperation(employee, connection);
       operation.execute();
    }

    @Override
    public Employee getEmployee(int id) {
        LoadEmployeeOperation loadEmployeeOperation = new LoadEmployeeOperation(id, connection);
        loadEmployeeOperation.execute();
        return loadEmployeeOperation.getEmployee();
    }

    @Override
    public void deleteEmployee(int empId) {

    }

    @Override
    public void addUnionMember(int memberId, Employee e) {

    }

    @Override
    public Employee getUnionMember(int memberId) {
        return null;
    }

    @Override
    public void removeUnionMember(int memberId) {

    }

    @Override
    public List<Integer> getAllEmployeeIds() {
        return null;
    }

    @Override
    public Employee[] getAllEmployees() {
        return new Employee[0];
    }
}
