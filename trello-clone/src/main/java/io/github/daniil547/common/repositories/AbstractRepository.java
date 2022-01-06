package io.github.daniil547.common.repositories;

import io.github.daniil547.common.db.DataSourceManager;
import io.github.daniil547.common.domain.Domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class AbstractRepository<T extends Domain> {
    boolean execute(String query) {
        try (Connection connection = DataSourceManager.getConnection()) {

            try (PreparedStatement stmnt = connection
                    .prepareStatement(query)) {
                return stmnt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns single Integer value.
     * If the given query retrieves multiple values from DB,
     * only the first one will be returned.
     *
     * @param query that requests a single integer value
     * @return retrieved integer value, null if nothing was retrieved or operation was unsuccessful
     */
    Integer retrieveInt(String query) {
        try (Connection connection = DataSourceManager.getConnection()) {

            try (PreparedStatement stmnt = connection
                    .prepareStatement(query)) {
                ResultSet resultSet = stmnt.executeQuery();
                resultSet.next();
                int anInt = resultSet.getInt(1);
                System.out.println("////inside retrieveInt, your int:" + anInt);
                return anInt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
