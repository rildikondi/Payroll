package com.akondi.business.packaging.payrolldatabaseimplementation;

import com.akondi.business.packaging.payrolldomain.PaymentClassification;
import com.akondi.business.packaging.payrolldomain.PaymentSchedule;
import com.akondi.business.packaging.payrolldomain.Affiliation;
import com.akondi.business.packaging.payrolldomain.Employee;
import com.akondi.business.packaging.payrolldomain.PaymentMethod;
import com.akondi.business.packaging.payrollimplementation.BiweeklySchedule;
import com.akondi.business.packaging.payrollimplementation.CommissionedClassification;
import com.akondi.business.packaging.payrollimplementation.DirectDepositMethod;
import com.akondi.business.packaging.payrollimplementation.HoldMethod;
import com.akondi.business.packaging.payrollimplementation.HourlyClassification;
import com.akondi.business.packaging.payrollimplementation.MailMethod;
import com.akondi.business.packaging.payrollimplementation.MonthlySchedule;
import com.akondi.business.packaging.payrollimplementation.SalariedClassification;
import com.akondi.business.packaging.payrollimplementation.SalesReceipt;
import com.akondi.business.packaging.payrollimplementation.TimeCard;
import com.akondi.business.packaging.payrollimplementation.UnionAffiliation;
import com.akondi.business.packaging.payrollimplementation.WeeklySchedule;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class SaveEmployeeOperation {
    private final Employee employee;
    private Connection connection;
    private String methodCode;
    private String classificationCode;
    private PreparedStatement insertPaymentMethodStatement;
    private PreparedStatement insertEmployeeStatement;
    private PreparedStatement insertClassificationCommand;

    public SaveEmployeeOperation(
            Employee employee, Connection connection) {
        this.employee = employee;
        this.connection = connection;
    }

    public void execute() {
        try {
            prepareToSavePaymentMethod(employee);
            prepareToSavePaymentClassification(employee);
            prepareToSaveEmployee(employee);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new Error(e.toString());
            }
            throw new Error(e.toString());
        }
    }

    private void prepareToSaveEmployee(Employee employee) {
        try {
            String sql = "INSERT INTO Employee (" +
                    "employerId, name, address, paymentClassification, paymentSchedule, paymentMethod, affiliation)" +
                    " VALUES(?, ?, ? , ?, ?, ?, ?)";
            insertEmployeeStatement = connection.prepareStatement(sql);
            insertEmployeeStatement.setInt(1, employee.getEmployerId());
            insertEmployeeStatement.setString(2, employee.getName());
            insertEmployeeStatement.setString(3, employee.getAddress());
            insertEmployeeStatement.setString(4, classificationCode);
            insertEmployeeStatement.setString(5, getScheduleCode(employee.getPaymentSchedule()));
            insertEmployeeStatement.setString(6, methodCode);
            insertEmployeeStatement.setString(7, getAffiliationCode(employee.getAffiliation()));
            insertEmployeeStatement.execute();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void prepareToSavePaymentMethod(Employee employee) {
        try {
            PaymentMethod method = employee.getPaymentMethod();
            if (method instanceof HoldMethod)
                methodCode = "hold";
            else if (method instanceof DirectDepositMethod) {
                methodCode = "direct deposit";
                DirectDepositMethod ddMethod = (DirectDepositMethod) method;
                createInsertDepositMethodStatement(ddMethod, employee);
            } else if (method instanceof MailMethod) {
                methodCode = "mail";
                MailMethod mailMethod = (MailMethod) method;
                createInsertMailMethodStatement(mailMethod, employee);
            } else
                methodCode = "unknown";

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void prepareToSavePaymentClassification(Employee employee) {
        try {
            PaymentClassification paymentClassification = employee.getPaymentClassification();
            if (paymentClassification instanceof HourlyClassification) {
                classificationCode = "hourly";
                HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
                insertHourlyClassificationStatement(hourlyClassification, employee);
            }
            else if (paymentClassification instanceof SalariedClassification) {
                classificationCode = "salaried";
                SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
                insertSalariedClassificationStatement(salariedClassification, employee);
            }
            else if (paymentClassification instanceof CommissionedClassification) {
                classificationCode = "commissioned";
                CommissionedClassification commissionedClassification = (CommissionedClassification) paymentClassification;
                insertCommissionedClassificationStatement(commissionedClassification, employee);
            } else
                classificationCode = "unknown";

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void insertSalariedClassificationStatement(SalariedClassification salariedClassification, Employee employee) {
        try {
            String sql = "INSERT INTO SalariedEmployee (employerId, salary)" +
                    "VALUES (?, ?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
            insertPaymentMethodStatement.setDouble(2, salariedClassification.getSalary());
            insertPaymentMethodStatement.execute();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void insertCommissionedClassificationStatement(CommissionedClassification commissionedClassification, Employee employee) {
        try {
            String sql = "INSERT INTO CommissionedEmployee (employerId, salary, commissionRate)" +
                    "VALUES (?, ?, ?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
            insertPaymentMethodStatement.setDouble(2, commissionedClassification.getSalary());
            insertPaymentMethodStatement.setDouble(3, commissionedClassification.getCommissionRate());
            insertPaymentMethodStatement.execute();

            for (SalesReceipt salesReceipt : commissionedClassification.getSalesReceiptMap().values()) {
                insertSalesReceiptStatement(salesReceipt);
            }

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void insertSalesReceiptStatement(SalesReceipt salesReceipt) {
        try {
            String sql = "INSERT INTO TimeCard (employerId, date, amount)" +
                    "VALUES (?, ?, ?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
            insertPaymentMethodStatement.setDate(2, new Date(salesReceipt.getDate().getTime()));
            insertPaymentMethodStatement.setDouble(3, salesReceipt.getAmount());
            insertPaymentMethodStatement.execute();

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void insertHourlyClassificationStatement(HourlyClassification hourlyClassification, Employee employee) {
        try {
            String sql = "INSERT INTO HourlyEmployee (employerId, hourlyRate)" +
                    "VALUES (?, ?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
            insertPaymentMethodStatement.setDouble(2, hourlyClassification.getHourlyRate());
            insertPaymentMethodStatement.execute();

            for (TimeCard timeCard : hourlyClassification.getTimeCardList().values()) {
                insertTimeCardStatement(timeCard);
            }

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void insertTimeCardStatement(TimeCard timeCard) {
        try {
            String sql = "INSERT INTO TimeCard (employerId, date, hours)" +
                    "VALUES (?, ?, ?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
            insertPaymentMethodStatement.setDate(2, new Date(timeCard.getDate().getTime()));
            insertPaymentMethodStatement.setDouble(3, timeCard.getHours());
            insertPaymentMethodStatement.execute();

        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void createInsertDepositMethodStatement(DirectDepositMethod ddMethod, Employee employee) {
        try {
            String sql = "INSERT INTO DirectDepositAccount (employerId, bank, account)" +
                    "VALUES (?, ?,?)";
            insertPaymentMethodStatement = connection.prepareStatement(sql);
            if (ddMethod.getBank() != null && ddMethod.getAccount() != null) {
                insertPaymentMethodStatement.setInt(1, employee.getEmployerId());
                insertPaymentMethodStatement.setString(2, ddMethod.getBank());
                insertPaymentMethodStatement.setString(3, ddMethod.getAccount());
                insertPaymentMethodStatement.execute();
            } else
                throw new Error("Arguments in DirectDepositMethod null");
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private void createInsertMailMethodStatement(MailMethod mailMethod, Employee employee) {
        try {
            String sql = "INSERT INTO PaycheckAddress(employerId, address) " +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employee.getEmployerId());
            preparedStatement.setString(2, mailMethod.getAddress());
            preparedStatement.execute();
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }

    private String getAffiliationCode(Affiliation affiliation) {
        if (affiliation instanceof UnionAffiliation)
            return "union";
        else return "unknown";

    }

    private String getScheduleCode(PaymentSchedule schedule) {
        if (schedule instanceof MonthlySchedule)
            return "monthly";
        if (schedule instanceof WeeklySchedule)
            return "weekly";
        if (schedule instanceof BiweeklySchedule)
            return "biweekly";
        else
            return "unknown";
    }
}