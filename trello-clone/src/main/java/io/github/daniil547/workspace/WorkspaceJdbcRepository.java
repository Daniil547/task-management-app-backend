package io.github.daniil547.workspace;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
class WorkspaceJdbcRepository extends PageJdbcRepository<Workspace> implements WorkspaceRepository {
    @Autowired
    protected WorkspaceJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "workspaces";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("company_website_url", "visibility");
    }

}
