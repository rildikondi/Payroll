package com.akondi.payroll.packaging.mvp.view;

import packaging.mvp.presenter.PayrollPresenter;

import javax.swing.*;
import java.awt.*;

public class PayrollWindow extends JFrame implements PayrollView {
    private JMenuBar mainMenu1;
    private JMenu menu;
    private JLabel pendingTransactionsLabel;
    private JLabel employeeLabel;
    public JTextField employeesTextBox;
    public JTextField transactionsTextBox;
    public JButton runButton;
    private Container components = null;
    private JMenuItem actionMenuItem;
    public JMenuItem addEmployeeMenuItem;
    private PayrollPresenter presenter;

    public PayrollWindow() {
        initializeComponent();
    }

    private void initializeComponent() {
        setTitle("Payroll Application");
        mainMenu1 = new JMenuBar();
        menu = new JMenu("Action");
        addEmployeeMenuItem = new JMenuItem("Add Employee");
        addEmployeeMenuItem.addActionListener(e -> presenter.addEmployeeActionInvoked());
        menu.add(addEmployeeMenuItem);
        mainMenu1.add(menu);
        this.setJMenuBar(mainMenu1);

        pendingTransactionsLabel = new JLabel("Pending Transactions");
        transactionsTextBox = new JTextField();
        runButton = new JButton("Run");
        runButton.addActionListener(e -> presenter.runTransactions());
        employeeLabel = new JLabel("Employee");
        employeesTextBox = new JTextField();

        JPanel pendingTransactionsPanel = new JPanel();
        pendingTransactionsPanel.add(pendingTransactionsLabel);
        pendingTransactionsPanel.add(transactionsTextBox);
        pendingTransactionsPanel.add(runButton);
        pendingTransactionsPanel.add(employeeLabel);
        pendingTransactionsPanel.add(employeesTextBox);
        pendingTransactionsPanel.setLayout(new BoxLayout(pendingTransactionsPanel, BoxLayout.Y_AXIS));
        this.add(pendingTransactionsPanel);

        this.setSize(600, 400);
        this.setLocation(300, 300);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (components != null) {
            components.removeAll();
        }

    }

    public void setTransactionsText(String text) {
        transactionsTextBox.setText(text);
    }

    public void setEmployeesText(String text) {
        employeesTextBox.setText(text);
    }

    public PayrollPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(PayrollPresenter payrollPresenter) {
        presenter = payrollPresenter;
    }
}

