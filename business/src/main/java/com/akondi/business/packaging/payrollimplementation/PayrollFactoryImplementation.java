package com.akondi.business.packaging.payrollimplementation;

import com.akondi.business.packaging.payrollfactory.PayrollFactory;

import java.util.Date;

public class PayrollFactoryImplementation extends PayrollFactory {
    @Override
    public BiweeklySchedule makeBiweeklySchedule() {
        return new BiweeklySchedule();
    }

    @Override
    public CommissionedClassification makeCommissionedClassification(double salary, double commissionRate) {
        return new CommissionedClassification(salary, commissionRate);
    }

    @Override
    public DirectDepositMethod makeDirectMethod(String bank, String account) {
        return new DirectDepositMethod(bank, account);
    }

    @Override
    public HoldMethod makeHoldMethod() {
        return new HoldMethod();
    }

    @Override
    public HourlyClassification makeHourlyClassification(double hourlyRate) {
        return new HourlyClassification(hourlyRate);
    }

    @Override
    public MailMethod makeMailMethod(String address) {
        return new MailMethod(address);
    }

    @Override
    public MonthlySchedule makeMonthlySchedule() {
        return new MonthlySchedule();
    }

    @Override
    public SalariedClassification makeSalariedClassification(double salary) {
        return new SalariedClassification(salary);
    }

    @Override
    public SalesReceipt makeSalesReceipt(Date date, double amount) {
        return new SalesReceipt(date, amount);
    }

    @Override
    public ServiceCharge makeServiceCharge(Date date, double charge) {
        return new ServiceCharge( date,  charge);
    }

    @Override
    public TimeCard makeTimeCard(Date date, double hours) {
        return new TimeCard(date, hours);
    }

    @Override
    public UnionAffiliation makeUnionAffiliation(int memberId, double dues) {
        return new UnionAffiliation(memberId, dues);
    }

    @Override
    public UnionAffiliation makeUnionAffiliation() {
        return new UnionAffiliation();
    }

    @Override
    public WeeklySchedule makeWeeklySchedule() {
        return new WeeklySchedule();
    }
}
