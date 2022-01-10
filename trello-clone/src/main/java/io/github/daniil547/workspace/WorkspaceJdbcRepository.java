package io.github.daniil547.workspace;

import io.github.daniil547.common.repositories.PageJdbcRepository;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

class WorkspaceJdbcRepository extends PageJdbcRepository<Workspace> {

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
        stmnt.setString(startingWith, entity.getCompanyWebsiteUrl().toString());
        stmnt.setString(startingWith + 1, entity.getVisibility().toString());
    }

    @Override
    protected Workspace fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        Workspace workspace = new Workspace();

        //the URL has to be checked for correctness before it is persisted, so the following should never throw
        try {
            workspace.setCompanyWebsiteUrl(new URL(resultSet.getString("company_website_url")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        workspace.setVisibility(WorkspaceVisibility.valueOf(resultSet.getString("visibility")));

        return workspace;
    }
}
