package io.github.daniil547.workspace;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    protected void fillEntitySpecificQueryParams(Workspace entity, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setString(startingWith, entity.getCompanyWebsiteUrl());
        stmnt.setString(startingWith + 1, entity.getVisibility().toString());
    }

    @Override
    protected Workspace fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        Workspace workspace = new Workspace();


        workspace.setCompanyWebsiteUrl(resultSet.getString("company_website_url"));

        workspace.setVisibility(WorkspaceVisibility.valueOf(resultSet.getString("visibility")));

        return workspace;
    }
}
