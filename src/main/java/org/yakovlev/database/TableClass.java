package org.yakovlev.database;

import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TableClass {
    public static ObservableList<?> createArrayFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    };
}
