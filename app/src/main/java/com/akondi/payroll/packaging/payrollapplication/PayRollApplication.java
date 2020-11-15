package com.akondi.payroll.packaging.payrollapplication;


import com.akondi.payroll.packaging.mvp.view.WindowViewLoader;
import com.akondi.payroll.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.payroll.packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import com.akondi.payroll.packaging.payrollfactory.PayrollFactory;
import com.akondi.payroll.packaging.payrollimplementation.PayrollFactoryImplementation;
import com.akondi.payroll.packaging.transactionfactory.TransactionFactory;
import com.akondi.payroll.packaging.transactionimplementation.TransactionFactoryImplementation;

public class PayRollApplication {

    public static void main(String[] args) {
//        PayrollDatabase payrollDatabase = new InMemoryPayrollDatabase();
//        TransactionFactory.transactionFactory = new TransactionFactoryImplementation(payrollDatabase);
//        PayrollFactory.payrollFactory = new PayrollFactoryImplementation();
//        TextParserTransactionSource source = new TextParserTransactionSource(TransactionFactory.transactionFactory, null);
//        TransactionApplication app = new TransactionApplication(source);
//        app.run();

        PayrollDatabase database = new InMemoryPayrollDatabase();
        TransactionFactory.transactionFactory = new TransactionFactoryImplementation(database);
        PayrollFactory.payrollFactory = new PayrollFactoryImplementation();
        WindowViewLoader viewLoader = new WindowViewLoader(database);
        viewLoader.loadPayrollView();
    }
}
