package com.akondi.payroll.packaging.payrollimplementation;

import com.akondi.payroll.packaging.payrolldomain.Affiliation;
import com.akondi.payroll.packaging.payrolldomain.Paycheck;
import com.akondi.payroll.packaging.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UnionAffiliation extends Affiliation {
    private Map<Date, ServiceCharge> serviceChargeMap = new HashMap<>();
    private int memberId;

    public int getMemberId() {
        return memberId;
    }

    private double dues;

    public UnionAffiliation(int memberId, double dues) {
        this.memberId = memberId;
        this.dues = dues;
    }

    public UnionAffiliation() {
    }

    public double getDues() {
        return dues;
    }

    @Override
    public ServiceCharge getServiceCharge(Date date) {
        return serviceChargeMap.get(date);
    }

    @Override
    public void addServiceCharge(ServiceCharge serviceCharge) {
        serviceChargeMap.put(serviceCharge.getDate(), serviceCharge);
    }

    @Override
    public double calculateDeductions(Paycheck paycheck) {
        double totalDues = 0;
        int fridays = numberOfFridaysInPayPeriod(paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate());
        double serviceCharges = 0;
        for (ServiceCharge serviceCharge : serviceChargeMap.values()) {
            if (DateUtil.isInPayPeriod(serviceCharge.getDate(), paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate()))
                serviceCharges += serviceCharge.getAmount();
        }
        totalDues = dues * fridays + serviceCharges;
        return totalDues;
    }

    private int numberOfFridaysInPayPeriod(Date payPeriodStart, Date payPeriodEnd) {
        int fridays = 0;
        int dayAdded = 1;
        int firstDay = getDayNumber(payPeriodStart);
        int lastDay = getDayNumber(payPeriodEnd);

        for (int day = firstDay; day <= lastDay; day++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(payPeriodStart);
            cal.add(Calendar.DAY_OF_MONTH, dayAdded);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
                fridays++;
            dayAdded++;
        }
        return fridays;
    }

    private int getDayNumber(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
