package com.akondi.payroll.packaging.payrollimplementation;

import java.util.Date;

public class ServiceCharge {
    private final Date date;
    private final double charge;

    public ServiceCharge(Date date, double charge) {
        this.date = date;
        this.charge = charge;
    }

    public double getAmount() {
        return charge;
    }

    public Date getDate() {
        return date;
    }
}
