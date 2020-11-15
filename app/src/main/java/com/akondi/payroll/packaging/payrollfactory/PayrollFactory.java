package com.akondi.payroll.packaging.payrollfactory;

import com.akondi.payroll.packaging.payrollimplementation.BiweeklySchedule;
import com.akondi.payroll.packaging.payrollimplementation.CommissionedClassification;
import com.akondi.payroll.packaging.payrollimplementation.DirectDepositMethod;
import com.akondi.payroll.packaging.payrollimplementation.HoldMethod;
import com.akondi.payroll.packaging.payrollimplementation.HourlyClassification;
import com.akondi.payroll.packaging.payrollimplementation.MailMethod;
import com.akondi.payroll.packaging.payrollimplementation.MonthlySchedule;
import com.akondi.payroll.packaging.payrollimplementation.SalariedClassification;
import com.akondi.payroll.packaging.payrollimplementation.SalesReceipt;
import com.akondi.payroll.packaging.payrollimplementation.ServiceCharge;
import com.akondi.payroll.packaging.payrollimplementation.TimeCard;
import com.akondi.payroll.packaging.payrollimplementation.UnionAffiliation;
import com.akondi.payroll.packaging.payrollimplementation.WeeklySchedule;

import java.util.Date;

public abstract class PayrollFactory {
    public static PayrollFactory payrollFactory;

    public abstract BiweeklySchedule makeBiweeklySchedule();

    public abstract CommissionedClassification makeCommissionedClassification(double salary, double commissionRate);

    public abstract DirectDepositMethod makeDirectMethod(String bank, String account);

    public abstract HoldMethod makeHoldMethod();

    public abstract HourlyClassification makeHourlyClassification(double hourlyRate);

    public abstract MailMethod makeMailMethod(String address);

    public abstract MonthlySchedule makeMonthlySchedule();

    public abstract SalariedClassification makeSalariedClassification(double salary);

    public abstract SalesReceipt makeSalesReceipt(Date date, double amount);

    public abstract ServiceCharge makeServiceCharge(Date date, double charge);

    public abstract TimeCard makeTimeCard(Date date, double hours);

    public abstract UnionAffiliation makeUnionAffiliation();

    public abstract UnionAffiliation makeUnionAffiliation(int memberId, double dues);

    public abstract WeeklySchedule makeWeeklySchedule();
}
