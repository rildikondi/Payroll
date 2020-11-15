package com.akondi.payroll.packaging.textparsertrasactionsource;

import com.akondi.payroll.packaging.transactionapplication.Transaction;
import com.akondi.payroll.packaging.transactionapplication.TransactionSource;
import com.akondi.payroll.packaging.transactionfactory.TransactionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextParserTransactionSource implements TransactionSource {
    private final TransactionFactory factory;
    private BufferedReader reader;

    public TextParserTransactionSource(TransactionFactory factory, InputStream input) {
        this.factory = factory;
        reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public Transaction getTransaction() {
        try {
            String line = reader.readLine();
            if (line == null) { return null; }
            return parseLine(line);
        } catch (IOException e) {
            throw new RuntimeException("unable to read transaction", e);
        }
    }

    private Transaction parseLine(String line) {
        String[] parts = line.split(" ");
        if (parts[4].equals("H")) {
            return factory.makeAddHourlyEmployee(integer(parts[1]), string(parts[2]), string(parts[3]), _double(parts[5]));
        } else {
            return factory.makeAddCommissionedEmployee(integer(parts[1]), string(parts[2]), string(parts[3]), _double(parts[5]), _double(parts[6]));
        }
    }

    private String string(String value) {
        return value.substring(1, value.length() - 1);
    }

    private Integer integer(String value) {
        return Integer.valueOf(value);
    }

    private Double _double(String value) {
        return Double.parseDouble(value);
    }
}
