package com.akondi.business.packaging.transactionimplementation;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrolldomain.Paycheck;
import com.akondi.business.packaging.transactionapplication.Transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PaydayTransaction  extends Transaction {
    private Date payDate;
    private Map<Integer, Paycheck> paychecks = new HashMap<>();

    public PaydayTransaction(Date payDate, PayrollDatabase payrollDatabase) {
        super(payrollDatabase);
        this.payDate = payDate;
    }

    @Override
    public void execute() {
        List<Integer> employeeIds =  payrollDatabase.getAllEmployeeIds();
        for (int empId : employeeIds) {
            Employee employee =  payrollDatabase.getEmployee(empId);
            if (employee.isPayDate(payDate)) {
                Date startDate = employee.getPayPeriodStartDate(payDate);
                Paycheck pc = new Paycheck(startDate, payDate);
                paychecks.put(empId, pc);
                employee.payday(pc);
            }
        }
    }

    public Paycheck getPaycheck(int empId) {
        return paychecks.get(empId);
    }
}
