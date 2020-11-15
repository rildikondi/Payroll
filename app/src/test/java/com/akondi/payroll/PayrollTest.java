package com.akondi.payroll;

import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.akondi.payroll.packaging.payrollfactory.PayrollFactory.payrollFactory;
import static com.akondi.payroll.packaging.transactionfactory.TransactionFactory.transactionFactory;

public class PayrollTest {
    private PayrollDatabase payrollDatabase;

    @Before
    public void init() {
        payrollDatabase = new InMemoryPayrollDatabase();
        transactionFactory = new TransactionFactoryImplementation(payrollDatabase);
        payrollFactory = new PayrollFactoryImplementation();
    }

    @Test
    public void testAddSalariedEmployee() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertEquals("Bob", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof SalariedClassification);
        SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
        Assert.assertEquals(1000.00, salariedClassification.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddHourlyEmployee() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Sam", "Home", 20.00);
        t.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertEquals("Sam", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof HourlyClassification);
        HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
        Assert.assertEquals(20.00, hourlyClassification.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testAddCommissionedEmployee() {
        int empId = 3;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "John", "Home", 1500.00, 15.00);
        t.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertEquals("John", e.getName());
        PaymentClassification paymentClassification = e.getPaymentClassification();
        Assert.assertTrue(paymentClassification instanceof CommissionedClassification);
        CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
        Assert.assertEquals(1500.00, commissionedClassification.getSalary(), .001);
        Assert.assertEquals(15.00, commissionedClassification.getCommissionRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof BiweeklySchedule);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void deleteEmployee() {
        int empId = 4;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "Bill", "Home", 2500, 3.2);
        t.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Transaction dt = transactionFactory.makeDeleteEmployeeTransaction(empId);
        dt.execute();
        e = payrollDatabase.getEmployee(empId);
        Assert.assertNull(e);
    }

    @Test
    public void testTimeCardTransaction() {
        int empId = 5;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date date = getDate("2005-7-31");
        Transaction tct = transactionFactory.makeTimeCardTransaction(date, 8.0, empId);
        tct.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = hc.getTimeCard(date);
        Assert.assertNotNull(tc);
        Assert.assertEquals(8.0, tc.getHours(), .001);
    }

    @Test
    public void testSalesReceiptTransaction() {
        int empId = 6;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(empId, "Bill", "Home", 1500.00, 15.00);
        t.execute();
        Date date = getDate("2005-7-31");
        Transaction salesReceiptTransaction = transactionFactory.makeSalesReceiptTransaction(date, 20.00, empId);
        salesReceiptTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification commissionedClassification = (CommissionedClassification) pc;
        Assert.assertNotNull(commissionedClassification);
        SalesReceipt salesReceipt = commissionedClassification.getSalesReceipt(date);
        Assert.assertEquals(20.00, salesReceipt.getAmount(), 0.001);
        Assert.assertEquals(date, salesReceipt.getDate());
    }

    @Test
    public void testAddServiceCharge() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        UnionAffiliation af = payrollFactory.makeUnionAffiliation();
        e.setAffiliation(af);
        int memberId = 86; // Maxwell Smart
        payrollDatabase.addUnionMember(memberId, e);
        Date date = getDate("2005-8-8");
        Transaction sct = transactionFactory.makeServiceChargeTransaction(memberId, date, 12.95);
        sct.execute();
        ServiceCharge sc = af.getServiceCharge(date);
        Assert.assertNotNull(sc);
        Assert.assertEquals(12.95, sc.getAmount(), .001);
    }

    @Test
    public void testChangeNameTransaction() {
        int empId = 2;
        Transaction transaction = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        transaction.execute();
        Transaction changeNameTransaction = transactionFactory.makeChangeNameTransaction(empId, "Bob");
        changeNameTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Assert.assertEquals("Bob", e.getName());
    }

    @Test
    public void testChangeHourlyTransaction() {
        int empId = 3;
        Transaction transaction = transactionFactory.makeAddCommissionedEmployee(
                empId, "Lance", "Home", 2500, 3.2);
        transaction.execute();
        Transaction changeHourlyTransaction = transactionFactory.makeChangeHourlyTransaction(empId, 27.52);
        changeHourlyTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        Assert.assertEquals(27.52, hc.getHourlyRate(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof WeeklySchedule);
    }

    @Test
    public void testChangeSalariedTransaction() {
        int empId = 7;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Sam", "Home", 1200.00);
        t.execute();
        Transaction changeSalariedTransaction = transactionFactory.makeChangeSalariedTransaction(empId, 1700.00);
        changeSalariedTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof SalariedClassification);
        SalariedClassification sc = (SalariedClassification) pc;
        Assert.assertEquals(1700.00, sc.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof MonthlySchedule);
    }

    @Test
    public void testChangeCommissionedTransaction() {
        int empId = 9;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(empId, "John", "Home", 1350.00, 22.50);
        t.execute();
        Transaction changeCommissionedTransaction = transactionFactory.makeChangeCommissionedTransaction(empId, 1400.00, 25.00);
        changeCommissionedTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        Assert.assertNotNull(pc);
        Assert.assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        Assert.assertEquals(1400.00, cc.getSalary(), .001);
        PaymentSchedule ps = e.getPaymentSchedule();
        Assert.assertTrue(ps instanceof BiweeklySchedule);
    }

    @Test
    public void testChangeDirectMethodTransaction() {
        int empId = 10;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Transaction changeMethodTransaction = transactionFactory.makeChangeDirectTransaction(empId, "Raiffeisen", "RZOOATL...");
        changeMethodTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof DirectDepositMethod);
        DirectDepositMethod dm = (DirectDepositMethod) pm;
        Assert.assertEquals("Raiffeisen", dm.getBank());
        Assert.assertEquals("RZOOATL...", dm.getAccount());
    }

    @Test
    public void testChangeMailMethodTransaction() {
        int empId = 10;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Transaction changeMailTransaction = transactionFactory.makeChangeMailTransaction(empId, "Home");
        changeMailTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof MailMethod);
        MailMethod mm = (MailMethod) pm;
        Assert.assertEquals("Home", mm.getAddress());

    }

    @Test
    public void testChangeHoldMethodTransaction() {
        int empId = 10;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Transaction changeHoldTransaction = transactionFactory.makeChangeHoldTransaction(empId);
        changeHoldTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        PaymentMethod pm = e.getPaymentMethod();
        Assert.assertNotNull(pm);
        Assert.assertTrue(pm instanceof HoldMethod);
    }

    @Test
    public void testChangeAffiliationTransaction() {
        int empId = 8;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        int memberId = 7743;
        Transaction cmt = transactionFactory.makeChangeMemberTransaction(empId, memberId, 99.42);
        cmt.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation affiliation = e.getAffiliation();
        Assert.assertNotNull(affiliation);
        Assert.assertTrue(affiliation instanceof UnionAffiliation);
        UnionAffiliation uf = (UnionAffiliation) affiliation;
        Assert.assertEquals(99.42, uf.getDues(), .001);
        Employee member = payrollDatabase.getUnionMember(memberId);
        Assert.assertNotNull(member);
        Assert.assertEquals(e, member);
    }

    @Test
    public void testChangeUnaffiliatedTransaction() {
        int empId = 8;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Transaction changeUnaffiliatedTransaction = transactionFactory.makeChangeUnaffiliatedTransaction(empId);
        changeUnaffiliatedTransaction.execute();
        Employee e = payrollDatabase.getEmployee(empId);
        Assert.assertNotNull(e);
        Affiliation affiliation = e.getAffiliation();
        Assert.assertNotNull(affiliation);
        Assert.assertSame(affiliation, Affiliation.NULL);
    }

    @Test
    public void testPaySingleSalariedEmployee() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        Date payDate = getDate("2001-11-30");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(1000.00, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(1000.00, pc.getNetPay(), .001);
    }

    @Test
    public void testPaySingleSalariedEmployeeOnWrongDate() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddSalariedEmployee(
                empId, "Bob", "Home", 1000.00);
        t.execute();
        Date payDate = getDate("2001-11-29");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPayingSingleHourlyEmployeeNoTimeCards() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 0.0);
    }

    private Date getDate(String date) {
        Calendar instance = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            instance.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Date.from(instance.toInstant());
    }

    private void validatePaycheck(PaydayTransaction pt, int empid, Date payDate, double pay) {
        Paycheck pc = pt.getPaycheck(empid);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(pay, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(pay, pc.getNetPay(), .001);
    }

    @Test
    public void testPaySingleHourlyEmployeeOneTimeCard() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        Transaction tc = transactionFactory.makeTimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 30.5);
    }

    @Test
    public void testPaySingleHourlyEmployeeOvertimeOneTimeCard() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(
                empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        Transaction tc = transactionFactory.makeTimeCardTransaction(payDate, 9.0, empId);
        tc.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, (8 + 1.5) * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeOnWrongDate() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-8"); // Thursday
        Transaction tc = transactionFactory.makeTimeCardTransaction(payDate, 9.0, empId);
        tc.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPaySingleHourlyEmployeeTwoTimeCards() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        Transaction tc = transactionFactory.makeTimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        Calendar cal = Calendar.getInstance();
        cal.setTime(payDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Transaction tc2 = transactionFactory.makeTimeCardTransaction(Date.from(cal.toInstant()), 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 7 * 15.25);
    }

    @Test
    public void testPaySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.25);
        t.execute();
        Date payDate = getDate("2001-11-9"); // Friday
        Date dateInPreviousPayPeriod = getDate("2001-11-2");
        Transaction tc = transactionFactory.makeTimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        Transaction tc2 = transactionFactory.makeTimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 2 * 15.25);
    }

    @Test
    public void testCommissionedEmployeeNoSales() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-26");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(700.00, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(0.0, pc.getDeductions(), .001);
        Assert.assertEquals(700.00, pc.getNetPay(), .001);
    }

    @Test
    public void testPayCommissionedEmployeeWrongDate() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-25");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNull(pc);
    }

    @Test
    public void testPayCommissionedEmployeeOneSale() {
        int empId = 2;
        Transaction t = new AddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07, payrollDatabase);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        Transaction salesReceiptTransaction = transactionFactory.makeSalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 707.00);
    }


    @Test
    public void testPayCommissionedEmployeeTwoSales() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        Transaction salesReceiptTransaction = transactionFactory.makeSalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        Date saleDate = getDate("2020-06-11");
        Transaction salesReceiptTransaction2 = transactionFactory.makeSalesReceiptTransaction(saleDate, 200.00, empId);
        salesReceiptTransaction2.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 721.00);
    }

    @Test
    public void testPayCommissionedEmployeeWithSalesSpanningTwoPayPeriods() {
        int empId = 2;
        Transaction t = transactionFactory.makeAddCommissionedEmployee(
                empId, "Bill", "Home", 1400.00, 0.07);
        t.execute();
        Date payDate = getDate("2020-06-12"); // second Friday
        Transaction salesReceiptTransaction = transactionFactory.makeSalesReceiptTransaction(payDate, 100.00, empId);
        salesReceiptTransaction.execute();
        Date saleDate = getDate("2020-06-13");// payed in fourth friday
        Transaction salesReceiptTransaction2 = transactionFactory.makeSalesReceiptTransaction(saleDate, 200.00, empId);
        salesReceiptTransaction2.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 707.00);
    }

    @Test
    public void testSalariedUnionMemberDues() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddSalariedEmployee(empId, "Bob", "Home", 1000.00);
        t.execute();
        int memberId = 7734;
        Transaction cmt = transactionFactory.makeChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-30");
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(1000.0, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(47.1, pc.getDeductions(), .001);
        Assert.assertEquals(1000.0 - 47.1, pc.getNetPay(), .001);
    }

    @Test
    public void testHourlyUnionMemberServiceCharge() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();
        int memberId = 7734;
        Transaction cmt = transactionFactory.makeChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-9");
        Transaction sct = transactionFactory.makeServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        Transaction tct = transactionFactory.makeTimeCardTransaction(payDate, 8.0, empId);
        tct.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
        Assert.assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
    }

    @Test
    public void testServiceChargesSpanningMultiplePayPeriods() {
        int empId = 1;
        Transaction t = transactionFactory.makeAddHourlyEmployee(empId, "Bill", "Home", 15.24);
        t.execute();
        int memberId = 7734;
        Transaction cmt = transactionFactory.makeChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Date payDate = getDate("2001-11-9");
        Date earlyDate = getDate("2001-11-2"); // previous Friday
        Date lateDate = getDate("2001-11-16"); // next Friday
        Transaction sct = transactionFactory.makeServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        Transaction sctEarly = transactionFactory.makeServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();
        Transaction serviceChargeTransaction = transactionFactory.makeServiceChargeTransaction(memberId, lateDate, 200.00);
        serviceChargeTransaction.execute();
        Transaction timeCardTransaction = transactionFactory.makeTimeCardTransaction(payDate, 8.0, empId);
        timeCardTransaction.execute();
        PaydayTransaction pt = (PaydayTransaction) transactionFactory.makePaydayTransaction(payDate);
        pt.execute();
        Paycheck pc = pt.getPaycheck(empId);
        Assert.assertNotNull(pc);
        Assert.assertEquals(payDate, pc.getPayPeriodEndDate());
        Assert.assertEquals(8 * 15.24, pc.getGrossPay(), .001);
        Assert.assertEquals("Hold", pc.getField("Disposition"));
        Assert.assertEquals(9.42 + 19.42, pc.getDeductions(), .001);
        Assert.assertEquals((8 * 15.24) - (9.42 + 19.42), pc.getNetPay(), .001);
    }
}
