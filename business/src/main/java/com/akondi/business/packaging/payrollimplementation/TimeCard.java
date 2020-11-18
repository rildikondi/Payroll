package com.akondi.business.packaging.payrollimplementation;

import java.util.Date;

public class TimeCard {
    private final Date date;
    private final double hours;

    public TimeCard(Date date, double hours) {
        this.date = date;
        this.hours = hours;
    }

    public double getHours() {
        return hours;
    }

    public Date getDate() {
        return date;
    }
}
