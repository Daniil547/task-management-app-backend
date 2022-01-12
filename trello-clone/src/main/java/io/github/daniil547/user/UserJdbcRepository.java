package io.github.daniil547.user;

import io.github.daniil547.common.repositories.PageJdbcRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class UserJdbcRepository extends PageJdbcRepository<User> implements UserRepository {

    protected UserJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("first_name", "last_name", "email");
    }

    @Override
    protected void tweakColumnNames(List<String> columns) {
        columns.add(columns.indexOf("page_name"), "username");
        columns.add(columns.indexOf("page_description"), "about");
    }

    @Override
    protected void fillEntitySpecificQueryParams(User entity, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setString(startingWith, entity.getFirstName());
        stmnt.setString(startingWith + 1, entity.getLastName());
        stmnt.setString(startingWith + 2, entity.getEmail());
    }

    @Override
    protected User fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));

        return user;
    }
}
