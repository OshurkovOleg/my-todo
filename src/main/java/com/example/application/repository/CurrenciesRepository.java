package com.example.application.repository;

import com.example.application.connection.ConnectionDB;
import com.example.application.exceptions.ConverterException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class CurrenciesRepository {
    private static final String ERROR_ADDING_VALUE_TO_LIST_CURRENCY = "Error adding value to database";
    private static final String ERROR_CONNECT_SQL = "Error connecting to sql server";
    private final static String SELECT_ALL_CURRENCIES = "select * from currency";
    private final static String INSERT_ONE_TYPE_CURRENCY_IN_TABLE = "insert into currency (name) values (?)";
    private final static String DELETE_ONE_TYPE_CURRENCY_FROM_TABLE = "delete from currency where name=(?)";

    //TODO нужно ли скрывать реализацию
    public static List<String> findCurrencies() {

        try (Statement statement = ConnectionDB.getInstance().createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_CURRENCIES)) {

            List<String> listCurrencies = new ArrayList<>();

            while (resultSet.next()) {
                listCurrencies.add(resultSet.getString(2));
            }

            return listCurrencies;

        } catch (SQLException e) {
            throw new ConverterException(ERROR_CONNECT_SQL + e);
        }
    }

    public static void save(String currency) {

        if (currency != null) {
            try (PreparedStatement preparedStatement = ConnectionDB.getInstance().prepareStatement(INSERT_ONE_TYPE_CURRENCY_IN_TABLE)) {
                preparedStatement.setString(1, currency);
                preparedStatement.executeUpdate();

            } catch (SQLException exp) {
                throw new ConverterException(ERROR_ADDING_VALUE_TO_LIST_CURRENCY + exp);
            }
        }
    }

    public static void delete(String currency) {

        if (currency != null) {
            try (PreparedStatement preparedStatement = ConnectionDB.getInstance().prepareStatement(DELETE_ONE_TYPE_CURRENCY_FROM_TABLE)) {
                preparedStatement.setString(1, currency);
                preparedStatement.executeUpdate();

            } catch (SQLException exp) {
                throw new ConverterException(ERROR_ADDING_VALUE_TO_LIST_CURRENCY + exp.getMessage());
            }
        }
    }
}
