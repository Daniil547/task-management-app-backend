package io.github.daniil547.user;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
class UserJdbcRepository extends PageJdbcRepository<User> implements UserRepository {

    @Autowired
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
        columns.remove("page_name");
        columns.add(columns.indexOf("page_description"), "about");
        columns.remove("page_description");
    }

}
