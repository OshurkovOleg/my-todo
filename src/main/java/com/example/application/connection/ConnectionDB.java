package com.example.application.connection;

import com.example.application.Settings;
import com.example.application.exceptions.ConverterException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String ERROR_INIT_DRIVER_DB = "Error initializing database driver";
    private static final String ERROR_CONNECT_SQL = "Error connecting to sql server";
    private static Connection instance;

    private ConnectionDB() {
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new ConnectionDB().connect();
            return instance;
        }

        return instance;
    }

    private Connection connect() {

        try {
            installDriverSql();

            return DriverManager.getConnection(Settings.SQL_SERVER,
                    Settings.SQL_USER,
                    Settings.SQL_PASSWORD);

        } catch (ConverterException | SQLException e) {
            System.err.println(ERROR_CONNECT_SQL + e);
        }
        return null;
    }

    private void installDriverSql() throws SQLException {

        try {
            Class<?> driverClass = Class.forName(Settings.POSTGRESQL_DRIVER);
            Driver driverPostgresql = (java.sql.Driver) driverClass.getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(driverPostgresql);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new ConverterException(ERROR_INIT_DRIVER_DB + " " + e.getMessage());
        }
    }
}
