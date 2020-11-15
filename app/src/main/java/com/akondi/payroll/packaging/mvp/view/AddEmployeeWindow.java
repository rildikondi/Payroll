package com.akondi.payroll.packaging.mvp.view;

import packaging.mvp.presenter.AddEmployeePresenter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class AddEmployeeWindow extends JFrame implements AddEmployeeView {

    public JTextField empIdTextBox;
    private JLabel empIdLabel;
    private JLabel nameLabel;
    public JTextField nameTextBox;
    private JLabel addressLabel;
    public JTextField addressTextBox;
    public JRadioButton hourlyRadioButton;
    public JRadioButton salaryRadioButton;
    public JRadioButton commissionRadioButton;
    private JLabel hourlyRateLabel;
    public JTextField hourlyRateTextBox;
    private JLabel salaryLabel;
    public JTextField salaryTextBox;
    private JLabel commissionSalaryLabel;
    public JTextField commissionSalaryTextBox;
    private JLabel commissionRateLabel;
    public JTextField commissionRateTextBox;
    private JTextField textBox2;
    private JLabel commissionRate;
    public JButton submitButton;
    public ButtonGroup paymentClassificationButtonGroup;
    private Component components = null;
    private AddEmployeePresenter presenter;

    public AddEmployeeWindow() {
        initializeComponent();
    }

    private void initializeComponent() {
        setTitle("Add Employee Window");
        empIdLabel = new JLabel("EmployerId: ");
        empIdTextBox = new JTextField();
        empIdTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setEmployerId(asInt(empIdTextBox.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setEmployerId(asInt(empIdTextBox.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setEmployerId(asInt(empIdTextBox.getText()));
            }
        });

        JPanel idPanel = new JPanel();
        idPanel.add(empIdLabel);
        idPanel.add(empIdTextBox);
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));

        nameLabel = new JLabel("Name: ");
        nameTextBox = new JTextField();
        nameTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setName(nameTextBox.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setName(nameTextBox.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setName(nameTextBox.getText());
            }
        });

        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameTextBox);
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));


        addressLabel = new JLabel("Address");
        addressTextBox = new JTextField();
        addressTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setAddress(addressTextBox.getText());
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setAddress(addressTextBox.getText());
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setAddress(addressTextBox.getText());
            }
        });

        JPanel addressPanel = new JPanel();
        addressPanel.add(addressLabel);
        addressPanel.add(addressTextBox);
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));


        JPanel basicPanel = new JPanel();
        basicPanel.add(idPanel);
        basicPanel.add(namePanel);
        basicPanel.add(addressPanel);
        basicPanel.setLayout(new BoxLayout(basicPanel, BoxLayout.Y_AXIS));
        this.add(basicPanel);

        hourlyRadioButton = new JRadioButton("Hourly");
        hourlyRadioButton.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                AbstractButton aButton = (AbstractButton) e.getSource();
                ButtonModel aModel = aButton.getModel();
                boolean armed = aModel.isArmed();
                boolean pressed = aModel.isPressed();
                boolean selected = aModel.isSelected();
                System.out.println("Changed: " + armed + "/" + pressed + "/" +
                        selected);

                hourlyRateTextBox.setEnabled(selected);
                presenter.setIsHourly(selected);
            }
        });

        salaryRadioButton = new JRadioButton("Salaried");
        salaryRadioButton.addChangeListener(e -> {

            salaryTextBox.setEnabled(salaryRadioButton.isSelected());
            presenter.setIsSalary(salaryRadioButton.isSelected());
        });

        commissionRadioButton = new JRadioButton("Commissioned");
        commissionRadioButton.addItemListener(e -> {
            commissionSalaryTextBox.setEnabled(commissionRadioButton.isSelected());
            commissionRateTextBox.setEnabled(commissionRadioButton.isSelected());
            presenter.setIsCommission(commissionRadioButton.isSelected());
        });

        paymentClassificationButtonGroup = new ButtonGroup();
        paymentClassificationButtonGroup.add(hourlyRadioButton);
        paymentClassificationButtonGroup.add(salaryRadioButton);
        paymentClassificationButtonGroup.add(commissionRadioButton);

        hourlyRateLabel = new JLabel("Rate");
        hourlyRateTextBox = new JTextField();
        hourlyRateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setHourlyRate(asDouble(hourlyRateTextBox.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setHourlyRate(asDouble(hourlyRateTextBox.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setHourlyRate(asDouble(hourlyRateTextBox.getText()));
            }
        });
        hourlyRateTextBox.setEnabled(false);
//
        JPanel ratePanel = new JPanel();
        ratePanel.add(hourlyRateLabel);
        ratePanel.add(hourlyRateTextBox);
        ratePanel.setLayout(new BoxLayout(ratePanel, BoxLayout.Y_AXIS));

        JPanel hourlyPanel = new JPanel();
        hourlyPanel.add(hourlyRadioButton);
        hourlyPanel.add(ratePanel);
        hourlyPanel.setLayout(new BoxLayout(hourlyPanel, BoxLayout.X_AXIS));


        salaryLabel = new JLabel("Salary");
        salaryTextBox = new JTextField();
        salaryTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setSalary(asDouble(salaryTextBox.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setSalary(asDouble(salaryTextBox.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setSalary(asDouble(salaryTextBox.getText()));
            }
        });
        salaryTextBox.setEnabled(false);


        JPanel salaryPanel = new JPanel();
        salaryPanel.add(salaryLabel);
        salaryPanel.add(salaryTextBox);
        salaryPanel.setLayout(new BoxLayout(salaryPanel, BoxLayout.Y_AXIS));

        JPanel salariedPanel = new JPanel();
        salariedPanel.add(salaryRadioButton);
        salariedPanel.add(salaryPanel);
        salariedPanel.setLayout(new BoxLayout(salariedPanel, BoxLayout.X_AXIS));


        commissionSalaryLabel = new JLabel("Salary");
        commissionSalaryTextBox = new JTextField();
        commissionSalaryTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setCommissionSalary(asDouble(commissionSalaryTextBox.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setCommissionSalary(asDouble(commissionSalaryTextBox.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setCommissionSalary(asDouble(commissionSalaryTextBox.getText()));
            }
        });
        commissionSalaryTextBox.setEnabled(false);

        commissionRateLabel = new JLabel("Rate");
        commissionRateTextBox = new JTextField();
        commissionRateTextBox.setEnabled(false);
        commissionRateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                presenter.setCommission(asDouble(commissionRateTextBox.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                presenter.setCommission(asDouble(commissionRateTextBox.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                presenter.setCommission(asDouble(commissionRateTextBox.getText()));
            }
        });

        JPanel commissionedDetailsPanel = new JPanel();
        commissionedDetailsPanel.add(commissionRateLabel);
        commissionedDetailsPanel.add(commissionRateTextBox);
        commissionedDetailsPanel.add(commissionSalaryLabel);
        commissionedDetailsPanel.add(commissionSalaryTextBox);
        commissionedDetailsPanel.setLayout(new BoxLayout(commissionedDetailsPanel, BoxLayout.Y_AXIS));


        JPanel commissionedPanel = new JPanel();
        commissionedPanel.add(commissionRadioButton);
        commissionedPanel.add(commissionedDetailsPanel);
        commissionedPanel.setLayout(new BoxLayout(commissionedPanel, BoxLayout.X_AXIS));


        JPanel detailsPanel = new JPanel();
        detailsPanel.add(hourlyPanel);
        detailsPanel.add(salariedPanel);
        detailsPanel.add(commissionedPanel);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));


        JPanel topPanel = new JPanel();
        topPanel.add(basicPanel);
        topPanel.add(detailsPanel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            presenter.addEmployee();
            dispose();
        });
        submitButton.setEnabled(false);

        JPanel mainPanel = new JPanel();
        mainPanel.add(topPanel);
        mainPanel.add(submitButton);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        this.add(mainPanel);

        this.setSize(600, 400);
        this.setLocation(400, 400);
    }

//    protected void dispose() {
//        Window win = SwingUtilities.getWindowAncestor(this);
//        win.dispose();

//    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public AddEmployeePresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(AddEmployeePresenter presenter) {
        this.presenter = presenter;
    }

    private double asDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private int asInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setSubmitEnabled(boolean value) {
        submitButton.setEnabled(value);
    }
}