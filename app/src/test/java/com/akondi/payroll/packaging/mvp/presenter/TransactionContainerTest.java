package com.akondi.payroll.packaging.mvp.presenter;

import com.akondi.payroll.packaging.transactionapplication.Transaction;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TransactionContainerTest {
    private TransactionContainer container;
    private boolean addActionCalled;
    private Transaction transaction;

    @Before
    public void setUp() {
        container = new TransactionContainer(new ArrayList<Transaction>());
        container.setAction(this::sillyAddAction);
        transaction = new MockTransaction(null);
    }

    @Test
    public void testConstruction() {
        Assert.assertEquals(0, container.getTransactions().size());
    }

    @Test
    public void testAddingTransaction() {
        container.add(transaction);
        List<Transaction> transactions = container.getTransactions();
        Assert.assertEquals(1, transactions.size());
        Assert.assertSame(transaction, transactions.get(0));
    }

    @Test
    public void testAddingTransactionTriggersDelegate() {
        container.add(transaction);
        Assert.assertTrue(addActionCalled);
    }

    private void sillyAddAction() {
        addActionCalled = true;
    }
}