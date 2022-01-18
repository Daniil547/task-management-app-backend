package io.github.daniil547.card_list;

import io.github.daniil547.common.repositories.PageJdbcRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

class CardListJdbcRepository extends PageJdbcRepository<CardList> implements CardListRepository {

    protected CardListJdbcRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "cardlists";
    }

    @Override
    protected List<String> getEntitySpecificColumns() {
        return List.of("board_id", "position", "active");
    }

    @Override
    protected void fillEntitySpecificQueryParams(CardList entity, PreparedStatement stmnt, Integer startingWith) throws SQLException {
        stmnt.setObject(startingWith, entity.getBoardId());
        stmnt.setInt(startingWith + 1, entity.getPosition());
        stmnt.setBoolean(startingWith + 2, entity.getActive());
    }

    @Override
    protected CardList fillEntitySpecificFields(ResultSet resultSet) throws SQLException {
        CardList cardList = new CardList();

        cardList.setBoardId(resultSet.getObject("board_id", UUID.class));
        cardList.setPosition(resultSet.getInt("position"));
        cardList.setActive(resultSet.getBoolean("active"));

        return cardList;
    }
}
