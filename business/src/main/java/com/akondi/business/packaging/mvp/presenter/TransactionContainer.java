package com.akondi.business.packaging.mvp.presenter;


import com.akondi.business.packaging.transactionapplication.Transaction;

import java.util.List;

public class TransactionContainer {
    private List<Transaction> transactions;

    public interface Action {
        void execute();
    }

    private Action action;


    public void setAction(Action action) {
        this.action = action;
    }

    public TransactionContainer(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
        if (action != null)
            action.execute();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void clear() {
        transactions.clear();
    }
}