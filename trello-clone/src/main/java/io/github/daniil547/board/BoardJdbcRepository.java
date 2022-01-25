package io.github.daniil547.board;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
class BoardJdbcRepository extends PageJdbcRepository<Board> implements BoardRepository {

    @Autowired
    protected BoardJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "boards";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("workspace_id", "visibility", "active");
    }

}
