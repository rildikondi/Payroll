package com.akondi.payroll.packaging.payrollimplementation;

import com.akondi.payroll.packaging.payrolldomain.Paycheck;
import com.akondi.payroll.packaging.payrolldomain.PaymentClassification;
import com.akondi.payroll.packaging.util.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;
    private Map<Date, TimeCard> timeCardList = new HashMap<>();

    public HourlyClassification(double hourlyRate) {
        super();
        this.hourlyRate = hourlyRate;
    }

    public Map<Date, TimeCard> getTimeCardList() {
        return timeCardList;
    }

    public void setTimeCardList(Map<Date, TimeCard> timeCardList) {
        this.timeCardList = timeCardList;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public TimeCard getTimeCard(Date from) {
        return timeCardList.get(from);
    }

    public void addTimeCard(TimeCard timeCard) {
        this.timeCardList.put(timeCard.getDate(), timeCard);
    }

    @Override
    public double calculatePay(Paycheck paycheck) {
        double totalPay = 0.00;
        for (TimeCard timeCard : timeCardList.values()) {
            if (DateUtil.isInPayPeriod(timeCard.getDate(), paycheck.getPayPeriodStartDate(), paycheck.getPayPeriodEndDate())) {
                totalPay += calculatePayForTimeCard(timeCard);
            }
        }
        return totalPay;
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        double overtimeHours = Math.max(0.0, timeCard.getHours() - 8);
        double normalHours = timeCard.getHours() - overtimeHours;
        return hourlyRate * (normalHours + 1.5 * overtimeHours);
    }
}
