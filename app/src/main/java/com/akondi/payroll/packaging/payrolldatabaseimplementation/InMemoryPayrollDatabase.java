package com.akondi.payroll.packaging.payrolldatabaseimplementation;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldomain.Employee;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class InMemoryPayrollDatabase implements PayrollDatabase {
    private Hashtable<Integer, Employee> employees = new Hashtable<>();
    private Hashtable<Integer, Employee> unionMembers = new Hashtable<>();

    @Override
    public void addEmployee(Employee employee) {
        employees.put(employee.getEmployerId(), employee);
    }

    @Override
    public Employee getEmployee(int id) {
        return employees.get(id);
    }

    @Override
    public void deleteEmployee(int empId) {
        employees.remove(empId);
    }

    @Override
    public void addUnionMember(int memberId, Employee e) {
        unionMembers.put(memberId, e);
    }

    @Override
    public Employee getUnionMember(int memberId) {
        return unionMembers.get(memberId);
    }

    @Override
    public void removeUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }

    @Override
    public List<Integer> getAllEmployeeIds() {
        return new ArrayList<>(employees.keySet());
    }

    @Override
    public Employee[] getAllEmployees() {
        return employees.values().toArray(new Employee[employees.values().size()]);
    }
}
