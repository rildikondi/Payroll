package com.akondi.java_ui.payrollapplication;

import com.akondi.business.packaging.payrolldatabase.PayrollDatabase;
import com.akondi.business.packaging.payrolldatabaseimplementation.InMemoryPayrollDatabase;
import com.akondi.business.packaging.payrollfactory.PayrollFactory;
import com.akondi.business.packaging.payrollimplementation.PayrollFactoryImplementation;
import com.akondi.business.packaging.transactionfactory.TransactionFactory;
import com.akondi.business.packaging.transactionimplementation.TransactionFactoryImplementation;
import com.akondi.java_ui.WindowViewLoader;

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
