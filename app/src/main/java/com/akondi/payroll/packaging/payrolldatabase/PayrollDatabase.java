package com.akondi.payroll.packaging.payrolldatabase;

import com.akondi.payroll.packaging.payrolldomain.Employee;

import java.util.List;

public interface PayrollDatabase {

    void addEmployee(Employee employee);

    Employee getEmployee(int id);

    void deleteEmployee(int empId);

    void addUnionMember(int memberId, Employee e);

    Employee getUnionMember(int memberId);

    void removeUnionMember(int memberId);

    List<Integer> getAllEmployeeIds();

    Employee[] getAllEmployees();
}
