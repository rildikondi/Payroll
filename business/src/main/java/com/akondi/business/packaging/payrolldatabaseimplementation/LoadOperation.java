package com.akondi.business.packaging.payrolldatabaseimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class LoadOperation {
    private int employerId;
    private Connection connection;
    private String tableName;

    public LoadOperation(int employerId, Connection connection) {
        this.employerId = employerId;
        this.connection = connection;
    }

    public abstract void execute();

    public  ResultSet loadData() throws Exception {
        if (tableName != null)
            return getPreparedStatement().executeQuery();
        else
            return null;
    }

    public PreparedStatement getPreparedStatement() throws Exception {
        String sql = String.format("SELECT * FROM %s where employerId = ?", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employerId);
        return preparedStatement;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
