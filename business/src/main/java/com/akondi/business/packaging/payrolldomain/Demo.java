package com.akondi.business.packaging.payrolldomain;

//import com.healthmarketscience.jackcess.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Demo {

    public static void main(String[] args) throws IOException, SQLException {
//        Database db = DatabaseBuilder.create(Database.FileFormat.V2000, new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("Employee")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("name")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("address")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("paymentClassification")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("paymentSchedule")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("paymentMethod")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("affiliation")
//                        .setSQLType(Types.VARCHAR))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("DirectDepositAccount")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("bank")
//                        .setSQLType(Types.VARCHAR))
//                .addColumn(new ColumnBuilder("account")
//                        .setSQLType(Types.VARCHAR))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("PaycheckAddress")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("address")
//                        .setSQLType(Types.VARCHAR))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("SalariedEmployee")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("salary")
//                        .setSQLType(Types.DOUBLE))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("HourlyEmployee")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("hourlyRate")
//                        .setSQLType(Types.DOUBLE))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("TimeCard")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("date")
//                        .setSQLType(Types.DATE))
//                .addColumn(new ColumnBuilder("hours")
//                        .setSQLType(Types.DOUBLE))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("CommissionedEmployee")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("salary")
//                        .setSQLType(Types.DOUBLE))
//                .addColumn(new ColumnBuilder("commissionRate")
//                        .setSQLType(Types.DOUBLE))
//                .toTable(db);
//        db.close();

//        Database db = DatabaseBuilder.open(new File("C:\\Users\\Amarildo\\Desktop\\payroll.mdb"));
//        Table newTable = new TableBuilder("SalesReceipt")
//                .addColumn(new ColumnBuilder("employerId")
//                        .setSQLType(Types.INTEGER))
//                .addColumn(new ColumnBuilder("date")
//                        .setSQLType(Types.DATE))
//                .addColumn(new ColumnBuilder("amount")
//                        .setSQLType(Types.DOUBLE))
//                .toTable(db);
//        db.close();

//        try {
//            Connection connection = DriverManager.getConnection("jdbc:ucanaccess://C:\\Users\\Amarildo\\Desktop\\payroll.mdb", "", "");
//            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//            String sql = "DROP TABLE PaycheckAddress";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.execute();
//
//        }catch (Exception e){
//            throw new Error(e.getMessage());
//        }


    }
}
