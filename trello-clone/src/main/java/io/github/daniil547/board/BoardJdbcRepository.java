package io.github.daniil547.board;

import io.github.daniil547.common.repositories.PageJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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

    @Override
    protected void fillEntitySpecificQueryParams(Board entity, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setObject(startingWith, entity.getWorkspaceId());
        stmnt.setString(startingWith + 1, entity.getVisibility().toString());
        stmnt.setBoolean(startingWith + 2, entity.getActive());
    }

    @Override
    protected Board fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        Board board = new Board();

        board.setWorkspaceId(resultSet.getObject("workspace_id", UUID.class));
        board.setVisibility(BoardVisibility.valueOf(resultSet.getString("visibility")));
        board.setActive(resultSet.getBoolean("active"));

        return board;
    }
}
