package com.akondi.business.packaging.payrollfactory;

import com.akondi.business.packaging.payrollimplementation.BiweeklySchedule;
import com.akondi.business.packaging.payrollimplementation.CommissionedClassification;
import com.akondi.business.packaging.payrollimplementation.DirectDepositMethod;
import com.akondi.business.packaging.payrollimplementation.HoldMethod;
import com.akondi.business.packaging.payrollimplementation.HourlyClassification;
import com.akondi.business.packaging.payrollimplementation.MailMethod;
import com.akondi.business.packaging.payrollimplementation.MonthlySchedule;
import com.akondi.business.packaging.payrollimplementation.SalariedClassification;
import com.akondi.business.packaging.payrollimplementation.SalesReceipt;
import com.akondi.business.packaging.payrollimplementation.ServiceCharge;
import com.akondi.business.packaging.payrollimplementation.TimeCard;
import com.akondi.business.packaging.payrollimplementation.UnionAffiliation;
import com.akondi.business.packaging.payrollimplementation.WeeklySchedule;

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
